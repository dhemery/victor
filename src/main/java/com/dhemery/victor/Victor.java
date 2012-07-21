package com.dhemery.victor;

import com.dhemery.configuration.Configuration;
import com.dhemery.configuration.ConfigurationException;
import com.dhemery.configuration.SingleSourceMappedCache;
import com.dhemery.network.*;
import com.dhemery.os.FactoryBasedShell;
import com.dhemery.os.Shell;
import com.dhemery.os.publishing.PublishingCommandFactory;
import com.dhemery.osx.AppleScriptShell;
import com.dhemery.publishing.Distributor;
import com.dhemery.publishing.EventBusPublisher;
import com.dhemery.victor.device.*;
import com.dhemery.victor.discovery.IosApplicationBundle;
import com.dhemery.victor.discovery.IosSdk;
import com.dhemery.victor.discovery.SdkItemKey;
import com.dhemery.victor.discovery.SdkItemSource;
import com.dhemery.victor.frank.Frank;
import com.dhemery.victor.frank.FrankApplication;
import com.dhemery.victor.frank.FrankViewAgent;
import com.dhemery.victor.frank.publishing.PublishingFrank;
import com.dhemery.victor.frankly.FranklyFrank;
import com.dhemery.victor.frankly.FranklyJsonCodec;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.eventbus.EventBus;

import java.util.List;

/**
 * A Victor environment configured according to specified configuration options.
 */
public class Victor {
    /**
     * The absolute path to the iOS application bundle to execute.
     * This is typically the application's .app package.
     */
    public static final String APPLICATION_BUNDLE_PATH = "victor.application.bundle.path";

    /**
     * The default value for the {@link #DEVICE_TYPE} option.
     */
    public static final String DEFAULT_DEVICE_TYPE = "iPhone";

    /**
     * The default value for the {@link #FRANK_HOST} option.
     */
    public static final String DEFAULT_FRANK_HOST = "localhost";

    /**
     * The default value for the {@link #FRANK_PORT} option.
     */
    public static final String DEFAULT_FRANK_PORT = "37265";

    /**
     * The default value for the {@link #SIMULATOR_PROCESS_OWNER} option.
     */
    public static final String DEFAULT_SIMULATOR_PROCESS_OWNER = "victor";

    /**
     * The type of device to simulate.
     * See your iOS Simulator's <em>Hardware > Device</em> menu for possible values.
     */
    public static final String DEVICE_TYPE = "victor.simulator.device.type";

    /**
     * The name of the host on which the Frank server listens for requests.
     * Do not include a scheme (e.g. "http://") at the start of this value.
     */
    public static final String FRANK_HOST = "victor.frank.host";

    /**
     * The port on which the Frank server listens for requests.
     */
    public static final String FRANK_PORT = "victor.frank.port";

    /**
     * The version of SDK to use to run the simulator.
     */
    public static final String SDK_VERSION = "victor.simulator.sdk.version";

    /**
     * <p>Specifies who is responsible for starting and stopping the simulator.</p>
     * <p>If the value is <strong>victor</strong>,
     * the constructed {@link IosDevice IosDevice}'s
     * {@link IosDevice#start() start()} method will launch the simulator,
     * and its {@link IosDevice#stop() stop()} method will shut it down.
     * </p>
     * <p>
     * If this option has any other value,
     * the constructed {@link IosDevice IosDevice}
     * will neither start nor stop the simulator.
     * Instead, the user must start and stop the simulator in some other way.
     * This is useful for experimenting.
     * It allows you to launch the application on your own,
     * manually execute preparatory steps to bring the application to a desired state,
     * then run an automated test against the prepared application.
     * </p>
     */
    public static final String SIMULATOR_PROCESS_OWNER = "victor.simulator.process.owner";
    public static final String FRANK_ENDPOINT_PROTOCOL = "http";

    private final Supplier<IosApplication> application = Suppliers.memoize(applicationSupplier());
    private final EventBusPublisher publisher = eventBusPublisher("Victor");
    private final Shell shell = shell(publisher);

    private final Configuration configuration;
    private IosApplicationBundle applicationBundle;
    private IosDevice device;
    private Frank frank;
    private IosSdk sdk;
    private IosViewAgent viewAgent;
    private IosViewFactory viewFactory;
    private Endpoint frankEndpoint;

    /**
     * Create a Victor factory with the specified configuration options.
     * @param configuration the configuration options for Victor.
     */
    public Victor(Configuration configuration) {
        this.configuration = new Configuration(configuration);
    }

    /**
     * The application bundle of the configured application.
     */
    public IosApplicationBundle applicationBundle() {
        if(applicationBundle == null) {
            applicationBundle = new IosApplicationBundle(configuration.requiredOption(APPLICATION_BUNDLE_PATH), shell);
        }
        return applicationBundle;
    }

    /**
     * A driver for the configured application.
     */
    public IosApplication application() {
        return application.get();
    }

    /**
     * A driver for the configured device.
     */
    public IosDevice device() {
        if(device == null) {
            AppleScriptShell appleScriptShell = new AppleScriptShell(shell);
            SimulatorApplication simulatorApplication = new SimulatorApplication(appleScriptShell);
            device = new SimulatedIosDevice(deviceType(), simulatorApplication, simulator());
        }
        return device;
    }

    public Distributor<Object> events() {
        return publisher;
    }

    /**
     * The Frank agent used by the application and by view agents.
     */
    public Frank frank() {
        if(frank == null) {
            Codec codec = new FranklyJsonCodec();
            frank = new PublishingFrank(publisher, new FranklyFrank(frankEndpoint(), codec));
        }
        return frank;
    }

    private Endpoint frankEndpoint() {
        if(frankEndpoint == null) {
            String host = option(FRANK_HOST, DEFAULT_FRANK_HOST);
            int port = Integer.parseInt(option(FRANK_PORT, DEFAULT_FRANK_PORT));
            Router router = new URLResourceRouter(FRANK_ENDPOINT_PROTOCOL);
            frankEndpoint = new RoutedEndpoint(router, host, port);
        }
        return frankEndpoint;
    }

    /**
     * The iOS SDK used to run the device and application.
     */
    public IosSdk sdk() {
        if(sdk == null) {
            sdk = findSdk();
        }
        return sdk;
    }

    /**
     * Report whether Victor owns the simulator.
     */
    public boolean victorOwnsSimulator() {
        String processOwner = option(SIMULATOR_PROCESS_OWNER, DEFAULT_SIMULATOR_PROCESS_OWNER);
        return processOwner.equals(DEFAULT_SIMULATOR_PROCESS_OWNER);
    }

    /**
     * The view agent that Victor's view factory uses to create view drivers.
     */
    public IosViewAgent viewAgent() {
        if(viewAgent == null) {
            viewAgent = new FrankViewAgent(frank());
        }
        return viewAgent;
    }

    /**
     * A factory that creates views backed by Victor's view agent.
     */
    public IosViewFactory viewFactory() {
        if(viewFactory == null) {
            viewFactory = new AgentBackedViewFactory(viewAgent());
        }
        return viewFactory;
    }




    private String applicationBinaryPath() {
        if(applicationBundle().isExecutable()) {
            return applicationBundle.pathToExecutable();
        }
        throw new ConfigurationException("Application binary is not executable: " + applicationBundle.pathToExecutable());
    }

    private String defaultDeviceType() {
        List<String> deviceTypes = applicationBundle().deviceTypes();
        if(deviceTypes.size() == 1) return deviceTypes.get(0);
        return DEFAULT_DEVICE_TYPE;
    }

    private String deviceType() {
        return option(DEVICE_TYPE, defaultDeviceType());
    }

    private static EventBusPublisher eventBusPublisher(String name) {
        EventBus eventBus = new EventBus(name);
        return new EventBusPublisher(eventBus);
    }

    private IosSdk findSdk() {
        SingleSourceMappedCache<SdkItemKey,String> sdkInfoCache = sdkInfoCache(shell);
        if(configuration.defines(SDK_VERSION)) {
            IosSdk userPreferredSdk = IosSdk.withVersion(sdkInfoCache, configuration.option(SDK_VERSION));
            if (userPreferredSdk.isInstalled()) return userPreferredSdk;
        }

        String canonicalName = applicationBundle.sdkCanonicalName();
        if(canonicalName != null) {
            IosSdk bundlePreferredSdk = IosSdk.withCanonicalName(sdkInfoCache, canonicalName);
            if (bundlePreferredSdk.isInstalled()) return bundlePreferredSdk;
        }

        IosSdk newestInstalledSdk = IosSdk.newest(sdkInfoCache);
        if (newestInstalledSdk.isInstalled()) return newestInstalledSdk;

        throw new ConfigurationException("No iphonesimulator SDK installed on this computer");
    }

    private Supplier<IosApplication> applicationSupplier() {
        return new Supplier<IosApplication>() {
            @Override
            public IosApplication get() {
                return new FrankApplication(frank());
            }
        };
    }

    private String option(String property, String defaultValue) {
        if(!configuration.defines(property)) {
            configuration.set(property, defaultValue);
        }
        return configuration.option(property);
    }

    private static SingleSourceMappedCache<SdkItemKey, String> sdkInfoCache(Shell shell) {
        SdkItemSource sdkItemSource = new SdkItemSource(shell);
        return new SingleSourceMappedCache<SdkItemKey, String>(sdkItemSource);
    }

    private static Shell shell(EventBusPublisher publisher) {
        PublishingCommandFactory commandFactory = new PublishingCommandFactory(publisher);
        return new FactoryBasedShell(commandFactory);
    }

    private Service simulator() {
        if (victorOwnsSimulator()) {
            String sdkPath = sdk().path();
            String simulatorBinaryPath = sdk().simulatorBinaryPath();
            String applicationBinaryPath = applicationBinaryPath();
            String deviceType = deviceType();
            return new VictorSimulatorProcess(sdkPath, simulatorBinaryPath, applicationBinaryPath, deviceType, shell);
        }
        return new UserSimulatorProcess();
    }
}

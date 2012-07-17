package com.dhemery.victor;

import com.dhemery.configuration.CacheSource;
import com.dhemery.configuration.Configuration;
import com.dhemery.configuration.SingleSourceMappedCache;
import com.dhemery.victor.device.SimulatedIosDevice;
import com.dhemery.victor.device.SimulatorApplication;
import com.dhemery.victor.device.UserSimulatorProcess;
import com.dhemery.victor.device.VictorSimulatorProcess;
import com.dhemery.victor.discovery.IosApplicationBundle;
import com.dhemery.victor.discovery.IosSdk;
import com.dhemery.victor.discovery.SdkItemKey;
import com.dhemery.victor.discovery.SdkItemSource;
import com.dhemery.victor.frank.Frank;
import com.dhemery.victor.frank.FrankApplication;
import com.dhemery.victor.frank.FrankViewAgent;
import com.dhemery.victor.frankly.FranklyFrank;
import com.dhemery.victor.frankly.FranklyJsonEncoder;
import com.dhemery.victor.http.HttpProtocol;
import com.dhemery.victor.io.*;
import com.dhemery.victor.os.Service;
import com.dhemery.victor.os.Shell;

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
     * the constructed {@link com.dhemery.victor.IosDevice IosDevice}'s
     * {@link com.dhemery.victor.IosDevice#start() start()} method will launch the simulator,
     * and its {@link com.dhemery.victor.IosDevice#stop() stop()} method will shut it down.
     * </p>
     * <p>
     * If this option has any other value,
     * the constructed {@link com.dhemery.victor.IosDevice IosDevice}
     * will neither start nor stop the simulator.
     * Instead, the user must start and stop the simulator in some other way.
     * This is useful for experimenting.
     * It allows you to launch the application on your own,
     * manually execute preparatory steps to bring the application to a desired state,
     * then run an automated test against the prepared application.
     * </p>
     */
    public static final String SIMULATOR_PROCESS_OWNER = "victor.simulator.process.owner";

    private final Configuration configuration;
    private IosApplication application;
    private IosApplicationBundle applicationBundle;
    private IosDevice device;
    private Frank frank;
    private IosSdk sdk;
    private Shell shell;
    private IosViewAgent viewAgent;
    private IosViewFactory viewFactory;

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
            applicationBundle = new IosApplicationBundle(configuration.requiredOption(APPLICATION_BUNDLE_PATH), shell());
        }
        return applicationBundle;
    }

    /**
     * A driver for the configured application.
     */
    public IosApplication application() {
        if(application == null) {
            application = new FrankApplication(frank());
        }
        return application;
    }

    /**
     * A driver for the configured device.
     */
    public IosDevice device() {
        if(device == null) {
            device = new SimulatedIosDevice(deviceType(), new SimulatorApplication(shell), simulator());
        }
        return device;
    }

    /**
     * The Frank agent used by the application and by view agents.
     */
    public Frank frank() {
        if(frank == null) {
            String host = option(FRANK_HOST, DEFAULT_FRANK_HOST);
            int port = Integer.parseInt(option(FRANK_PORT, DEFAULT_FRANK_PORT));
            JsonEncoder encoder = new FranklyJsonEncoder();
            Protocol http = new HttpProtocol();
            //TODO: construct endpoint.
            Endpoint endpoint = null;
            frank = new FranklyFrank(http, endpoint, encoder);
        }
        return frank;
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
     * The shell that Victor and its creations use to run OS commands.
     */
    public Shell shell() {
        if(shell == null) {
            shell = new Shell();
        }
        return shell;
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

    private IosSdk findSdk() {
        CacheSource<SdkItemKey,String> sdkInfoSource = new SdkItemSource(shell());
        SingleSourceMappedCache<SdkItemKey,String> sdkInfoCache = new SingleSourceMappedCache<SdkItemKey, String>(sdkInfoSource);
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

    private String option(String property, String defaultValue) {
        if(!configuration.defines(property)) {
            configuration.set(property, defaultValue);
        }
        return configuration.option(property);
    }

    private Service simulator() {
        if (victorOwnsSimulator()) {
            String sdkPath = sdk().path();
            String simulatorBinaryPath = sdk().simulatorBinaryPath();
            String applicationBinaryPath = applicationBinaryPath();
            String deviceType = deviceType();
            return new VictorSimulatorProcess(shell, sdkPath, simulatorBinaryPath, applicationBinaryPath, deviceType);
        }
        return new UserSimulatorProcess();
    }
}

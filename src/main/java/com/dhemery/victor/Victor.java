package com.dhemery.victor;

import com.dhemery.configuration.Configuration;
import com.dhemery.configuration.ContextItemCache;
import com.dhemery.victor.configuration.IosApplicationBundle;
import com.dhemery.victor.configuration.IosSdk;
import com.dhemery.victor.configuration.SdkInfoCache;
import com.dhemery.victor.device.SimulatedIosDevice;
import com.dhemery.victor.device.SimulatorApplication;
import com.dhemery.victor.device.UserSimulatorProcess;
import com.dhemery.victor.device.VictorSimulatorProcess;
import com.dhemery.victor.frank.FrankApplication;
import com.dhemery.victor.frank.FrankViewAgent;
import com.dhemery.victor.frank.FranklyAgent;
import com.dhemery.victor.frank.JsonEndpoint;
import com.dhemery.victor.frankly.EndpointFranklyAgent;
import com.dhemery.victor.io.Endpoint;
import com.dhemery.victor.os.Service;
import com.dhemery.victor.os.Shell;

import java.util.List;

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
     * The value of the {@link #FRANK_HOST} option
     * if the user does not supply a value.
     */
    public static final String DEFAULT_FRANK_HOST = "localhost";

    /**
     * The value of the {@link #FRANK_PORT} option
     * if the user does not supply a value.
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
    private Endpoint endpoint;
    private FranklyAgent frankly;
    private IosSdk sdk;
    private ContextItemCache sdkInfo;
    private Shell shell;
    private IosViewAgent viewAgent;

    /**
     * Create a Victor factory according to the specified configuration.
     * @param configuration the configuration options for Victor.
     */
    public Victor(Configuration configuration) {
        this.configuration = new Configuration(configuration);
    }

    /**
     * Return the application bundle of the configured application.
     * @return the application bundle of the configured application.
     */
    public IosApplicationBundle applicationBundle() {
        if(applicationBundle == null) {
            applicationBundle = new IosApplicationBundle(shell(), configuration.requiredOption(APPLICATION_BUNDLE_PATH));
        }
        return applicationBundle;
    }

    /**
     * Return the configured iOS application.
     * @return the configured iOS application.
     */
    public IosApplication application() {
        if(application == null) {
            application = new FrankApplication(franklyAgent());
        }
        return application;
    }

    /**
     * Return the configured iOS device.
     * @return the configured iOS device.
     */
    public IosDevice device() {
        if(device == null) {
            device = new SimulatedIosDevice(new SimulatorApplication(shell), simulator());
        }
        return device;
    }

    /**
     * Return the type of device that will be simulated.
     * @return the type of device that will be simulated.
     */
    public String deviceType() {
        return option(DEVICE_TYPE, defaultDeviceType());
    }

    /**
     * Return the name of the Frank server's host.
     * @return the name of the Frank server's host.
     */
    public String frankHost() {
        return option(FRANK_HOST, DEFAULT_FRANK_HOST);
    }

    /**
     * Return the Frankly agent that backs the application and view agents.
     * @return the Frankly agent that backs the application and view agents.
     */
    public FranklyAgent franklyAgent() {
        if(frankly == null) {
            frankly = new EndpointFranklyAgent(frankServerEndpoint());
        }
        return frankly;
    }

    /**
     * Return the port on which the Frank server listens for requests.
     * @return the port on which the Frank server listens for requests.
     */
    public long frankPort() {
        return Long.parseLong(option(FRANK_PORT, DEFAULT_FRANK_PORT));
    }

    /**
     * Return the endpoint that represents the Frank server.
     * @return the endpoint that represents the Frank server.
     */
    public Endpoint frankServerEndpoint() {
        if(endpoint == null) {
            String frankServerUrl = String.format("%s:%s", frankHost(), frankPort());
            endpoint = new JsonEndpoint(frankServerUrl);
        }
        return endpoint;
    }

    /**
     * Return the SDK that will be used to run the simulated device and application.
     * @return the SDK that will be used to run the simulated device and application.
     */
    public IosSdk sdk() {
        if(sdk == null) {
            initializeSdk();
        }
        return sdk;
    }

    /**
     * Return the shell that Victor and its creations will use to run OS commands.
     * @return the shell that Victor and its creations will use to run OS commands.
     */
    public Shell shell() {
        if(shell == null) {
            shell = new Shell();
        }
        return shell;
    }

    /**
     * Indicate whether Victor owns the simulator.
     * @return whether Victor owns the simulator.
     */
    public boolean victorOwnsSimulator() {
        String processOwner = option(SIMULATOR_PROCESS_OWNER, DEFAULT_SIMULATOR_PROCESS_OWNER);
        return processOwner.equals(DEFAULT_SIMULATOR_PROCESS_OWNER);
    }

    /**
     * Return the view agent that backs all iOS views.
     * @return the view agent that backs all iOS views.
     */
    public IosViewAgent viewAgent() {
        if(viewAgent == null) {
            viewAgent = new FrankViewAgent(franklyAgent());
        }
        return viewAgent;
    }





    private String applicationBinaryPath() {
        if(applicationBundle.isExecutable()) {
            return applicationBundle.pathToExecutable();
        }
        throw new VictorConfigurationException("Application binary is not executable: " + applicationBundle.pathToExecutable());
    }

    private String defaultDeviceType() {
        List<String> deviceTypes = applicationBundle.deviceTypes();
        if(deviceTypes.size() == 1) return deviceTypes.get(0);
        return DEFAULT_DEVICE_TYPE;
    }

    private void initializeSdk() {
        if(configuration.defines(SDK_VERSION)) {
            sdk = IosSdk.withVersion(sdkInfo(), configuration.option(SDK_VERSION));
            if (sdk.isInstalled()) return;
        }

        String canonicalName = applicationBundle.sdkCanonicalName();
        if(canonicalName != null) {
            sdk = IosSdk.withCanonicalName(sdkInfo(), canonicalName);
            if (sdk.isInstalled()) return;
        }

        sdk = IosSdk.newest(sdkInfo());
        if (sdk.isInstalled()) return;

        throw new VictorConfigurationException("No iphonesimulator SDK installed on this computer");
    }

    private String option(String property, String defaultValue) {
        if(!configuration.defines(property)) {
            configuration.set(property, defaultValue);
        }
        return configuration.option(property);
    }

    private ContextItemCache sdkInfo() {
        if(sdkInfo == null) {
            sdkInfo = new SdkInfoCache(shell());
        }
        return sdkInfo;
    }

    private Service simulator() {
        return victorOwnsSimulator() ? victorSimulatorProcess() : userSimulatorProcess();
    }

    private String simulatorBinaryPath() {
        return sdk().simulatorBinaryPath();
    }

    private Service userSimulatorProcess() {
        return new UserSimulatorProcess();
    }

    private Service victorSimulatorProcess() {
        String sdkPath = sdk().path();
        String simulatorBinaryPath = simulatorBinaryPath();
        String applicationBinaryPath = applicationBinaryPath();
        String deviceType = deviceType();
        return new VictorSimulatorProcess(shell, sdkPath, simulatorBinaryPath, applicationBinaryPath, deviceType);
    }
}

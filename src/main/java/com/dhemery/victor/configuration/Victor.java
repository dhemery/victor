package com.dhemery.victor.configuration;

import com.dhemery.configuration.Configuration;
import com.dhemery.configuration.ContextItemCache;
import com.dhemery.victor.IosApplication;
import com.dhemery.victor.IosDevice;
import com.dhemery.victor.device.SimulatedIosDevice;
import com.dhemery.victor.device.local.SimulatorApplication;
import com.dhemery.victor.device.local.UserSimulatorProcess;
import com.dhemery.victor.device.local.VictorSimulatorProcess;
import com.dhemery.victor.frank.FrankAgent;
import com.dhemery.victor.frank.FrankIosApplication;
import com.dhemery.victor.frank.MessageListener;
import com.dhemery.victor.os.CommandListener;
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
    private final IosApplicationBundle applicationBundle;
    private final Shell shell;
    private IosSdk sdk;
    private ContextItemCache sdkInfo;
    private FrankAgent frankAgent;
    private IosApplication application;
    private IosDevice device;

    /**
     * Create a Victor factory according to the specified configuration.
     * @param configuration the configuration options for Victor.
     */
    public Victor(Configuration configuration) {
        shell = new Shell();
        sdkInfo = new SdkInfoCache(shell);
        this.configuration = new Configuration(configuration);
        applicationBundle = new IosApplicationBundle(shell, configuration.requiredOption(APPLICATION_BUNDLE_PATH));
    }

    public void addMessageListener(MessageListener listener) {
        frankAgent().addListener(listener);
    }

    public void addCommandListener(CommandListener listener) {
        shell.addListener(listener);
    }

    public IosApplicationBundle applicationBundle() {
        return applicationBundle;
    }

    public IosApplication application() {
        if(application == null) {
            application = new FrankIosApplication(frankAgent());
        }
        return application;
    }

    public IosDevice device() {
        if(device == null) {
            device = new SimulatedIosDevice(new SimulatorApplication(shell), simulator());
        }
        return device;
    }

    public String deviceType() {
        return option(DEVICE_TYPE, defaultDeviceType());
    }

    public String frankHost() {
        return option(FRANK_HOST, DEFAULT_FRANK_HOST);
    }

    public long frankPort() {
        return Long.parseLong(option(FRANK_PORT, DEFAULT_FRANK_PORT));
    }

    public IosSdk sdk() {
        if(sdk == null) { initializeSdk(); }
        return sdk;
    }

    public void removeMessageListener(MessageListener listener) {
        frankAgent().removeListener(listener);
    }

    public void removeCommandListener(CommandListener listener) {
        shell.removeListener(listener);
    }

    public boolean victorOwnsSimulator() {
        String processOwner = option(SIMULATOR_PROCESS_OWNER, DEFAULT_SIMULATOR_PROCESS_OWNER);
        return processOwner.equals(DEFAULT_SIMULATOR_PROCESS_OWNER);
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

    private FrankAgent frankAgent() {
        if(frankAgent == null) {
            String url = String.format("http://%s:%s", frankHost(), frankPort());
            frankAgent =new FrankAgent(url);
        }
        return frankAgent;
    }

    private void initializeSdk() {
        if(configuration.defines(SDK_VERSION)) {
            sdk = IosSdk.withVersion(sdkInfo, configuration.option(SDK_VERSION));
            if (sdk.isInstalled()) return;
        }

        String canonicalName = applicationBundle.sdkCanonicalName();
        if(canonicalName != null) {
            sdk = IosSdk.withCanonicalName(sdkInfo, canonicalName);
            if (sdk.isInstalled()) return;
        }

        sdk = IosSdk.newest(sdkInfo);
        if (sdk.isInstalled()) return;

        throw new VictorConfigurationException("No iphonesimulator SDK installed on this computer");
    }

    private String option(String property, String defaultValue) {
        if(!configuration.defines(property)) {
            configuration.set(property, defaultValue);
        }
        return configuration.option(property);
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

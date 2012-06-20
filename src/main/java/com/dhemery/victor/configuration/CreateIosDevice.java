package com.dhemery.victor.configuration;


import com.dhemery.configuration.Configuration;
import com.dhemery.victor.IosDevice;
import com.dhemery.victor.VictorEntryPoint;
import com.dhemery.victor.device.SimulatedIosDevice;
import com.dhemery.victor.device.SimulatorAgent;
import com.dhemery.victor.device.local.UserSimulatorAgent;
import com.dhemery.victor.device.local.VictorSimulatorAgent;
import com.dhemery.victor.discovery.IosSdk;

import java.util.List;

/**
 * <p>
 * Create a {@link SimulatedIosDevice} configured according to a set of configuration options.
 * See individual fields for descriptions, property names, and default values for the available options.
 * </p>
 * <p>
 * <strong>NOTE:</strong>
 * This class cannot provide a default value
 * for the {@link #APPLICATION_BUNDLE_PATH APPLICATION_BUNDLE_PATH} option.
 * The configuration must define a value.
 * </p>
 */
@VictorEntryPoint
public class CreateIosDevice {
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
     * The default value for the {@link #SIMULATOR_PROCESS_OWNER} option.
     */
    public static final String DEFAULT_SIMULATOR_PROCESS_OWNER = "victor";

    /**
     * The type of device to simulate.
     * See your iOS Simulator's <em>Hardware > Device</em> menu for possible values.
     */
    public static final String DEVICE_TYPE = "victor.simulator.device.type";

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

    private final Configuration configuration;
    private final IosApplicationBundle applicationBundle;

    /**
     * <p>
     * Create a {@link SimulatedIosDevice} configured according to {@code configuration}.
     * </p>
     * @param configuration defines the configuration options.
     * @return a {@link SimulatedIosDevice} configured as specified.
     */
    @VictorEntryPoint
    public static IosDevice withConfiguration(Configuration configuration) {
        return new CreateIosDevice(configuration).device();
    }

    private CreateIosDevice(Configuration configuration) {
        this.configuration = new Configuration(configuration);
        applicationBundle = new IosApplicationBundle(configuration.requiredOption(APPLICATION_BUNDLE_PATH));
    }

    private String applicationBinaryPath() {
        String applicationBinary = applicationBundle.pathToExecutable();
        if(applicationBundle.isExecutable()) {
            return applicationBinary;
        }
        throw new IosDeviceConfigurationException("Application binary is not executable: " + applicationBinary);
    }

    private String defaultDeviceType() {
        List<String> deviceTypes = applicationBundle.deviceTypes();
        if(deviceTypes.size() == 1) return deviceTypes.get(0);
        return DEFAULT_DEVICE_TYPE;
    }

    private IosDevice device() {
        return new SimulatedIosDevice(simulator());
    }

    private String deviceType() {
        if(!configuration.defines(DEVICE_TYPE)) {
            configuration.set(DEVICE_TYPE, defaultDeviceType());
        }
        return configuration.option(DEVICE_TYPE);
    }

    private IosSdk sdk() {
        IosSdk sdk;
        if(configuration.defines(SDK_VERSION)) {
            String version = configuration.option(SDK_VERSION);
            sdk = IosSdk.withVersion(version);
            if(sdk.isInstalled()) return sdk;
        }

        IosApplicationBundle bundle = new IosApplicationBundle(configuration.requiredOption(APPLICATION_BUNDLE_PATH));
        String canonicalName = bundle.sdkCanonicalName();
        if(canonicalName != null) {
            sdk = IosSdk.withCanonicalName(canonicalName);
            if(sdk.isInstalled()) return sdk;
        }

        sdk = IosSdk.newest();
        if(sdk.isInstalled()) return sdk;

        throw new IosDeviceConfigurationException("No iphonesimulator SDK installed on this computer");
    }

    private SimulatorAgent simulator() {
        if (victorOwnsSimulator()) return victorSimulatorAgent();
        return userSimulatorAgent();
    }

    private String simulatorBinaryPath() {
        return IosSdk.simulatorBinaryPath();
    }

    private String simulatorProcessOwner() {
        if(!configuration.defines(SIMULATOR_PROCESS_OWNER)) configuration.set(SIMULATOR_PROCESS_OWNER, DEFAULT_SIMULATOR_PROCESS_OWNER);
        return configuration.option(SIMULATOR_PROCESS_OWNER);
    }

    private SimulatorAgent userSimulatorAgent() {
        return new UserSimulatorAgent();
    }

    private boolean victorOwnsSimulator() {
        return simulatorProcessOwner().equals(DEFAULT_SIMULATOR_PROCESS_OWNER);
    }

    private SimulatorAgent victorSimulatorAgent() {
        return new VictorSimulatorAgent(sdk().path(), simulatorBinaryPath(), applicationBinaryPath(), deviceType());
    }
}

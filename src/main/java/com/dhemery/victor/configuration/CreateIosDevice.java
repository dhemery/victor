package com.dhemery.victor.configuration;


import com.dhemery.victor.IosDevice;
import com.dhemery.victor.device.SimulatedIosDevice;
import com.dhemery.victor.device.SimulatorAgent;
import com.dhemery.victor.device.UserSimulatorAgent;
import com.dhemery.victor.device.VictorSimulatorAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.dhemery.victor.configuration.IosDeviceConfigurationOptions.*;

/**
 * <p>
 * Create a {@link com.dhemery.victor.device.SimulatedIosDevice} configured according to a set of configuration options.
 * See {@link IosDeviceConfigurationOptions} for descriptions, property names, and default values for the available options.
 * </p>
 * <p>
 * <strong>NOTE:</strong>
 * This class cannot provide a default value
 * for the {@link IosDeviceConfigurationOptions#APPLICATION_BINARY_PATH APPLICATION_BINARY_PATH} option.
 * The configuration must define a value.
 * </p>
 */
public class CreateIosDevice {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Configuration configuration;

    /**
     * <p>
     * Create a {@link com.dhemery.victor.device.SimulatedIosDevice} configured according to {@code configuration}.
     * @param configuration defines the configuration options.
     * @return a {@link com.dhemery.victor.device.SimulatedIosDevice} configured as specified.
     */
    public static IosDevice withConfiguration(Configuration configuration) {
        return new CreateIosDevice(configuration).device();
    }

    private CreateIosDevice(Configuration configuration) {
        this.configuration = defaultConfiguration();
        this.configuration.merge(configuration);
    }

    private Configuration defaultConfiguration() {
        Configuration defaultConfiguration = new Configuration();
        defaultConfiguration.set(IosDeviceConfigurationOptions.SDK_VERSION, DEFAULT_SDK_VERSION);
        defaultConfiguration.set(DEVICE_TYPE, DEFAULT_DEVICE_TYPE);
        defaultConfiguration.set(SIMULATOR_PROCESS_OWNER, DEFAULT_SIMULATOR_PROCESS_OWNER);
        return defaultConfiguration;
    }

    private IosDevice device() {
        return new SimulatedIosDevice(simulator());
    }

    private String deviceType() {
        return configuration.option(DEVICE_TYPE);
    }

    private String applicationBinaryPath() {
        String applicationBinaryPath = configuration.option(IosDeviceConfigurationOptions.APPLICATION_BINARY_PATH);
        if (applicationBinaryPath != null) return applicationBinaryPath;
        String explanation = String.format("Configuration option %s not defined", IosDeviceConfigurationOptions.APPLICATION_BINARY_PATH);
        throw new IosDeviceConfigurationException(explanation);
    }

    private String sdkPath() {
        return XcodeBuild.sdkPathForVersion(sdkVersion());
    }

    private String sdkVersion() {
        return configuration.option(IosDeviceConfigurationOptions.SDK_VERSION);
    }

    private SimulatorAgent simulator() {
        if (victorOwnsSimulator()) return victorSimulatorAgent();
        return userSimulatorAgent();
    }

    private String simulatorBinaryPath() {
        return XcodeBuild.simulatorBinaryPath();
    }

    private String simulatorProcessOwner() {
        return configuration.option(SIMULATOR_PROCESS_OWNER);
    }

    private SimulatorAgent userSimulatorAgent() {
        return new UserSimulatorAgent();
    }

    private boolean victorOwnsSimulator() {
        return simulatorProcessOwner().equals("victor");
    }

    private VictorSimulatorAgent victorSimulatorAgent() {
        return new VictorSimulatorAgent(sdkPath(), simulatorBinaryPath(), applicationBinaryPath(), deviceType());
    }
}

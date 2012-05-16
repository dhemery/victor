package com.dhemery.victor.configuration;


import com.dhemery.configuration.Configuration;
import com.dhemery.victor.IosDevice;
import com.dhemery.victor.device.SimulatedIosDevice;
import com.dhemery.victor.device.SimulatorAgent;
import com.dhemery.victor.device.local.UserSimulatorAgent;
import com.dhemery.victor.device.local.VictorSimulatorAgent;
import com.dhemery.victor.discovery.IosSdk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.dhemery.victor.configuration.IosDeviceConfigurationOptions.*;

/**
 * <p>
 * Create a {@link SimulatedIosDevice} configured according to a set of configuration options.
 * See {@link IosDeviceConfigurationOptions} for descriptions, property names, and default values for the available options.
 * </p>
 * <p>
 * <strong>NOTE:</strong>
 * This class cannot provide a default value
 * for the {@link IosDeviceConfigurationOptions#APPLICATION_BUNDLE_PATH APPLICATION_BUNDLE_PATH} option.
 * The configuration must define a value.
 * </p>
 */
public class CreateIosDevice {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Configuration configuration;
    private final IosApplicationBundle applicationBundle;

    /**
     * <p>
     * Create a {@link SimulatedIosDevice} configured according to {@code configuration}.
     * @param configuration defines the configuration options.
     * @return a {@link SimulatedIosDevice} configured as specified.
     */
    public static IosDevice withConfiguration(Configuration configuration) {
        return new CreateIosDevice(configuration).device();
    }

    private CreateIosDevice(Configuration configuration) {
        this.configuration = defaultConfiguration();
        this.configuration.merge(configuration);
        applicationBundle = new IosApplicationBundle(configuration.requiredOption(APPLICATION_BUNDLE_PATH));
    }

    private String applicationBinaryPath() {
        String applicationBinary = applicationBundle.pathToExecutable();
        if(applicationBundle.isExecutable()) {
            return applicationBinary;
        }
        throw new IosDeviceConfigurationException("Application binary is not executable: " + applicationBinary);
    }

    private Configuration defaultConfiguration() {
        Configuration defaultConfiguration = new Configuration();
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

    private IosSdk sdk() {
        return FindSdk.withConfiguration(configuration);
    }

    private SimulatorAgent simulator() {
        if (victorOwnsSimulator()) return victorSimulatorAgent();
        return userSimulatorAgent();
    }

    private String simulatorBinaryPath() {
        return IosSdk.simulatorBinaryPath();
    }

    private String simulatorProcessOwner() {
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

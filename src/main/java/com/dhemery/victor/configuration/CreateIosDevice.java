package com.dhemery.victor.configuration;


import com.dhemery.configuration.Configuration;
import com.dhemery.victor.IosDevice;
import com.dhemery.victor.device.SimulatedIosDevice;
import com.dhemery.victor.device.local.SimulatorApplication;
import com.dhemery.victor.device.local.UserSimulatorProcess;
import com.dhemery.victor.device.local.VictorSimulatorProcess;
import com.dhemery.victor.os.Service;

/**
 * <p>
 * Create a {@link SimulatedIosDevice} configured according to a set of configuration options.
 * See {@link VictorConfiguration} for descriptions, property names, and default values for the available options.
 * </p>
 * <p>
 * <strong>NOTE:</strong>
 * This class cannot provide a default value
 * for the {@link VictorConfiguration#APPLICATION_BUNDLE_PATH APPLICATION_BUNDLE_PATH} option.
 * The configuration must define a value.
 * </p>
 */
public class CreateIosDevice {
    private final VictorConfiguration configuration;

    /**
     * <p>
     * Create a {@link SimulatedIosDevice} configured according to {@code configuration}.
     * </p>
     * @param configuration defines the configuration options.
     * @return a {@link SimulatedIosDevice} configured as specified.
     */
    public static IosDevice withConfiguration(Configuration configuration) {
        return new CreateIosDevice(configuration).device();
    }

    private CreateIosDevice(Configuration configuration) {
        this.configuration = new VictorConfiguration(configuration);
    }

    private String applicationBinaryPath() {
        IosApplicationBundle bundle = configuration.applicationBundle();
        if(bundle.isExecutable()) {
            return bundle.pathToExecutable();
        }
        throw new IosDeviceConfigurationException("Application binary is not executable: " + bundle.pathToExecutable());
    }

    private IosDevice device() {
        return new SimulatedIosDevice(new SimulatorApplication(), simulator());
    }

    private String deviceType() {
        return configuration.deviceType();
    }

    private IosSdk sdk() {
        IosSdk sdk = configuration.sdk();
        if(sdk.isInstalled()) return sdk;
        throw new IosDeviceConfigurationException("No iphonesimulator SDK installed on this computer");
    }

    private Service simulator() {
        return victorOwnsSimulator() ? victorSimulatorProcess() : userSimulatorProcess();
    }

    private String simulatorBinaryPath() {
        return IosSdk.simulatorBinaryPath();
    }

    private Service userSimulatorProcess() {
        return new UserSimulatorProcess();
    }

    private boolean victorOwnsSimulator() {
        return configuration.victorOwnsSimulator();
    }

    private Service victorSimulatorProcess() {
        String sdkPath = sdk().path();
        String simulatorBinaryPath = simulatorBinaryPath();
        String applicationBinaryPath = applicationBinaryPath();
        String deviceType = deviceType();
        return new VictorSimulatorProcess(sdkPath, simulatorBinaryPath, applicationBinaryPath, deviceType);
    }
}

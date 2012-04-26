package com.dhemery.victor.device;


import com.dhemery.victor.IosDevice;
import com.dhemery.victor.xcode.Xcode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.dhemery.victor.device.IosDeviceConfiguration.*;

/**
 * Create a simulated iOS device.
 */
public class CreateIosDevice {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final IosDeviceConfiguration configuration;
    private final Xcode xcode = new Xcode();

    /**
     * <p>Create a simulated iOS device configured according to {@code configuration}.</p>
     * <p>Notes:</p>
     * <ol>
     * <li>
     * If the configuration does not define a value
     * for {@link IosDeviceConfiguration#APPLICATION_BINARY_PATH_PROPERTY_NAME APPLICATION_BINARY_PATH_PROPERTY_NAME},
     * this method throws an exception.
     * </li>
     * <li>
     * If the configuration does not define a value
     * for {@link IosDeviceConfiguration#SDK_ROOT_PROPERTY_NAME SDK_ROOT_PROPERTY_NAME},
     * this method obtains a default value
     * by calling {@link Xcode#newestSdkRoot()}.
     * </li>
     * <li>
     * If the configuration does not define a value
     * for {@link IosDeviceConfiguration#SIMULATOR_BINARY_PATH_PROPERTY_NAME SIMULATOR_BINARY_PATH_PROPERTY_NAME},
     * this method obtains a default value
     * by calling {@link Xcode#simulatorBinaryPath()}.
     * </li>
     * </ol>
     *
     * @param configuration specifies the configuration options.
     * @return a simulated device configured as specified.
     */
    public static IosDevice withConfiguration(IosDeviceConfiguration configuration) {
        return new CreateIosDevice(configuration).device();
    }

    private CreateIosDevice(IosDeviceConfiguration configuration) {
        this.configuration = configuration;
    }

    private IosDevice device() {
        Simulator simulator = new LocalSimulator(sdkRoot(), simulatorBinaryPath(), applicationBinaryPath());
        return new SimulatedIosDevice(simulator);
    }

    private String applicationBinaryPath() {
        String applicationBinaryPath = configuration.getApplicationBinaryPath();
        if (applicationBinaryPath != null) return applicationBinaryPath;
        String explanation = String.format("Configuration option %s not defined", APPLICATION_BINARY_PATH_PROPERTY_NAME);
        throw new IosDeviceConfigurationException(explanation);
    }

    private String sdkRoot() {
        String sdkRoot = configuration.getSdkRoot();
        if (sdkRoot != null) return sdkRoot;
        sdkRoot = xcode.newestSdkRoot();
        log.trace("Configuration option {} not defined. Using default value {}", SDK_ROOT_PROPERTY_NAME, sdkRoot);
        return sdkRoot;
    }

    private String simulatorBinaryPath() {
        String simulatorBinaryPath = configuration.getSimulatorBinaryPath();
        if (simulatorBinaryPath != null) return simulatorBinaryPath;
        simulatorBinaryPath = xcode.simulatorBinaryPath();
        log.trace("Configuration option {} not defined. Using default value {}", SIMULATOR_BINARY_PATH_PROPERTY_NAME, simulatorBinaryPath);
        return simulatorBinaryPath;
    }
}

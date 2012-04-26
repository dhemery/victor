package com.dhemery.victor.device;


import com.dhemery.victor.IosDevice;
import com.dhemery.victor.xcode.Xcode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.dhemery.victor.device.IosDeviceConfigurationProperties.*;

/**
 * Create a simulated iOS device.
 */
public class CreateIosDevice {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final IosDeviceConfiguration configuration;
    private final Xcode xcode = new Xcode();

    /**
     * <p>Create a simulated iOS device configured according to {@code configuration}.</p>
     * <p>Notes about configuration properties:</p>
     * <dl>
     * <dt>{@link IosDeviceConfigurationProperties#APPLICATION_BINARY_PATH APPLICATION_BINARY_PATH}</dt>
     * <dd>
     * If the configuration does not define a value for this property,
     * this method throws an exception.
     * </dd>
     * <dt>{@link IosDeviceConfigurationProperties#SDK_ROOT SDK_ROOT}</dt>
     * <dd>
     * If the configuration does not define a value for this property,
     * this method obtains a default value
     * by calling {@link Xcode#newestSdkRoot()}.
     * </dd>
     * <dt>{@link IosDeviceConfigurationProperties#SIMULATOR_BINARY_PATH SIMULATOR_BINARY_PATH}</dt>
     * <dd>
     * If the configuration does not define a value for this property,
     * this method obtains a default value
     * by calling {@link Xcode#simulatorBinaryPath()}.
     * </dd>
     * </dl>
     *
     * @param configuration specifies the configuration properties.
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
        String explanation = String.format("Configuration option %s not defined", APPLICATION_BINARY_PATH);
        throw new IosDeviceConfigurationException(explanation);
    }

    private String sdkRoot() {
        String sdkRoot = configuration.getSdkRoot();
        if (sdkRoot != null) return sdkRoot;
        sdkRoot = xcode.newestSdkRoot();
        log.trace("Configuration option {} not defined. Using default value {}", SDK_ROOT, sdkRoot);
        return sdkRoot;
    }

    private String simulatorBinaryPath() {
        String simulatorBinaryPath = configuration.getSimulatorBinaryPath();
        if (simulatorBinaryPath != null) return simulatorBinaryPath;
        simulatorBinaryPath = xcode.simulatorBinaryPath();
        log.trace("Configuration option {} not defined. Using default value {}", SIMULATOR_BINARY_PATH, simulatorBinaryPath);
        return simulatorBinaryPath;
    }
}

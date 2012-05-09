package com.dhemery.victor.device;


import com.dhemery.victor.Configuration;
import com.dhemery.victor.IosDevice;
import com.dhemery.victor.xcode.Xcode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Scanner;

import static com.dhemery.victor.device.IosDeviceConfigurationOptions.*;

/**
 * Create a simulated iOS device.
 */
public class CreateIosDevice {
    /**
     * If the user does not supply a value for the {@link IosDeviceConfigurationOptions#DEVICE_TYPE DEVICE_TYPE} property,
     * Victor simulates an iPhone device with a non-retina display.
     */
    public static final String DEFAULT_DEVICE_TYPE = "iPhone";

    /**
     * If the user does not supply a value for the {@link IosDeviceConfigurationOptions#SIMULATOR_PROCESS_OWNER SIMULATOR_PROCESS_OWNER} property,
     * Victor launches a simulator and shuts it down.
     */
    public static final String DEFAULT_SIMULATOR_PROCESS_OWNER = "victor";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Configuration configuration;
    private final Xcode xcode = new Xcode();

    /**
     * <p>Create a simulated iOS device configured according to {@code configuration}.</p>
     * <p>Notes about configuration properties:</p>
     * <dl>
     * <dt>{@link IosDeviceConfigurationOptions#APPLICATION_BINARY_PATH APPLICATION_BINARY_PATH}</dt>
     * <dd>
     * If the configuration does not define a value for this property,
     * this method throws an exception.
     * </dd>
     * <dt>{@link IosDeviceConfigurationOptions#SIMULATOR_PROCESS_OWNER SIMULATOR_PROCESS_OWNER}</dt>
     * <dd>
     * If the configuration does not define a value for this property,
     * or if the configured value is "victor",
     * Victor will launch its own simulator.
     * If the configured value is any other value,
     * Victor will attach to an already-running simulator.
     * </dd>
     * <dt>{@link IosDeviceConfigurationOptions#DEVICE_TYPE DEVICE_TYPE}</dt>
     * <dd>
     * If the configuration does not define a value for this property,
     * Victor will simulate an iPhone with a non-retina display.
     * </dd>
     * <dt>{@link IosDeviceConfigurationOptions#SDK_ROOT SDK_ROOT}</dt>
     * <dd>
     * If the configuration does not define a value for this property,
     * this method obtains a default value
     * by calling {@link Xcode#newestSdkRoot()}.
     * </dd>
     * <dt>{@link IosDeviceConfigurationOptions#SIMULATOR_BINARY_PATH SIMULATOR_BINARY_PATH}</dt>
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
    public static IosDevice withConfiguration(Configuration configuration) {
        return new CreateIosDevice(configuration).device();
    }

    private CreateIosDevice(Configuration configuration) {
        this.configuration = configuration;
    }

    private IosDevice device() {
        return new SimulatedIosDevice(simulator());
    }

    private String deviceType() {
        String deviceType = configuration.option(DEVICE_TYPE);
        if (deviceType != null) return deviceType;
        return DEFAULT_DEVICE_TYPE;
    }

    private String applicationBinaryPath() {
        String applicationBinaryPath = configuration.option(APPLICATION_BINARY_PATH);
        if (applicationBinaryPath != null) return applicationBinaryPath;
        String explanation = String.format("Configuration option %s not defined", APPLICATION_BINARY_PATH);
        throw new IosDeviceConfigurationException(explanation);
    }

    private String sdkRoot() {
        String sdkRoot = configuration.option(SDK_ROOT);
        if (sdkRoot != null) return sdkRoot;
        sdkRoot = xcode.newestSdkRoot();
        log.trace("Configuration option {} not defined. Using default value {}", SDK_ROOT, sdkRoot);
        return sdkRoot;
    }

    /**
     * Note: This method is misplaced, and will likely move elsewhere.
     * @return the version number configured by the configuration.
     */
    public String getSdkVersion() {
        Scanner sdkNameScanner = new Scanner(new File(sdkRoot()).getName());
        sdkNameScanner.skip("iPhoneSimulator");
        Integer major = sdkNameScanner.nextInt();
        sdkNameScanner.skip(".");
        Integer minor = sdkNameScanner.nextInt();
        return String.format("%d.%d", major, minor);
    }

    private Simulator simulator() {
        if (victorOwnsSimulator()) return victorOwnedSimulator();
        return userOwnedSimulator();
    }

    private String simulatorBinaryPath() {
        String simulatorBinaryPath = configuration.option(SIMULATOR_BINARY_PATH);
        if (simulatorBinaryPath != null) return simulatorBinaryPath;
        simulatorBinaryPath = xcode.simulatorBinaryPath();
        log.trace("Configuration option {} not defined. Using default value {}", SIMULATOR_BINARY_PATH, simulatorBinaryPath);
        return simulatorBinaryPath;
    }

    private String simulatorProcessOwner() {
        String simulatorProcessOwner = configuration.option(SIMULATOR_PROCESS_OWNER);
        if (simulatorProcessOwner != null) return simulatorProcessOwner;
        return DEFAULT_SIMULATOR_PROCESS_OWNER;
    }

    private Simulator userOwnedSimulator() {
        return new UserOwnedSimulator();
    }

    private LocalSimulator victorOwnedSimulator() {
        return new LocalSimulator(sdkRoot(), simulatorBinaryPath(), applicationBinaryPath(), deviceType());
    }

    private boolean victorOwnsSimulator() {
        return simulatorProcessOwner().equals("victor");
    }
}

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
 * Create a {@link SimulatedIosDevice} configured according to a set of configuration options.
 */
public class CreateIosDevice {
    /**
     * The default value for the {@link IosDeviceConfigurationOptions#DEVICE_TYPE DEVICE_TYPE} option.
     */
    public static final String DEFAULT_DEVICE_TYPE = "iPhone";

    /**
     * The default value for the {@link IosDeviceConfigurationOptions#SIMULATOR_PROCESS_OWNER SIMULATOR_PROCESS_OWNER} option.
     */
    public static final String DEFAULT_SIMULATOR_PROCESS_OWNER = "victor";

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Configuration configuration;
    private final Xcode xcode = new Xcode();

    /**
     * <p>
     * Create a {@link SimulatedIosDevice} configured according to {@code configuration}.
     * See {@link IosDeviceConfigurationOptions} for names and descriptions of the available options.
     * </p>
     * <p>
     * The configuration must supply a value for
     * {@link IosDeviceConfigurationOptions#APPLICATION_BINARY_PATH APPLICATION_BINARY_PATH}.
     * </p>
     * <p>This method supplies default values for other undefined configuration options as follows:</p>
     * <table>
     * <tr><th style="text-align:left">Option</th><th style="text-align:left">Default value</th></tr>
     * <tr>
     * <td>{@link IosDeviceConfigurationOptions#SIMULATOR_PROCESS_OWNER SIMULATOR_PROCESS_OWNER}</td>
     * <td>The value of {@link #DEFAULT_SIMULATOR_PROCESS_OWNER}</td>
     * </tr>
     * <tr>
     * <td>{@link IosDeviceConfigurationOptions#DEVICE_TYPE DEVICE_TYPE}</td>
     * <td>The value of {@link #DEFAULT_DEVICE_TYPE}</td>
     * </tr>
     * <tr>
     * <td>{@link IosDeviceConfigurationOptions#SDK_ROOT SDK_ROOT}</td>
     * <td>The value returned from {@link Xcode#newestSdkRoot()}</td>
     * </tr>
     * <tr>
     * <td>{@link IosDeviceConfigurationOptions#SIMULATOR_BINARY_PATH SIMULATOR_BINARY_PATH}</td>
     * <td>The value returned form {@link Xcode#simulatorBinaryPath()}</td>
     * </tr>
     * </table>
     *
     * @param configuration defines the configuration options.
     * @return a {@link SimulatedIosDevice} configured as specified.
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
     *
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

    private SimulatorAgent simulator() {
        if (victorOwnsSimulator()) return victorSimulatorAgent();
        return userSimulatorAgent();
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

    private SimulatorAgent userSimulatorAgent() {
        return new UserSimulatorAgent();
    }

    private VictorSimulatorAgent victorSimulatorAgent() {
        return new VictorSimulatorAgent(sdkRoot(), simulatorBinaryPath(), applicationBinaryPath(), deviceType());
    }

    private boolean victorOwnsSimulator() {
        return simulatorProcessOwner().equals("victor");
    }
}

package com.dhemery.victor.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class IosDeviceConfiguration {
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Specifies path to the iOS application binary file to execute.
     * This is typically a file inside the application's .app package.
     * The file's executable flag must be set.
     */
    public static final String APPLICATION_BINARY_PATH = "victor.application.binary.path";

    /**
     * Specifies the path to the root directory of the iOS SDK with which to launch the simulator.
     */
    public static final String SDK_ROOT = "victor.sdk.root";

    /**
     * Specifies the path to the iOS Simulator executable file.
     */
    public static final String SIMULATOR_BINARY_PATH = "victor.simulator.binary.path";

    private final Map<String,String> options = new HashMap<String, String>();

    /**
     * Create an {@code IosDeviceConfiguration} with no options defined.
     */
    public IosDeviceConfiguration() {}

    /**
     * Create an {@code IosDeviceConfiguration} with options copied from the given map;
     * @param options a map that defines options.
     */
    public IosDeviceConfiguration(Map<String, String> options) {
        this.options.putAll(options);
    }

    public String getApplicationBinaryPath() {
        return options.get(APPLICATION_BINARY_PATH);
    }

    public void setApplicationBinaryPath(String applicationBinaryPath) {
        options.put(APPLICATION_BINARY_PATH, applicationBinaryPath);
    }

    public String getSdkRoot() {
        return options.get(SDK_ROOT);
    }

    public void setSdkRoot(String sdkRoot) {
        options.put(SDK_ROOT, sdkRoot);
    }

    public String getSimulatorBinaryPath() {
        return options.get(SIMULATOR_BINARY_PATH);
    }

    public void setSimulatorBinaryPath(String simulatorBinaryPath) {
        options.put(SIMULATOR_BINARY_PATH, simulatorBinaryPath);
    }
}
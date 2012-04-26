package com.dhemery.victor.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Configuration options used by {@link CreateIosDevice} to create devices.</p>
 */
public class IosDeviceConfiguration {
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Specifies path to the iOS application binary file to execute.
     * This is typically a file inside the application's .app package.
     * The file's executable flag must be set.
     */
    public static final String APPLICATION_BINARY_PATH_PROPERTY_NAME = "victor.application.binary.path";

    /**
     * Specifies the path to the root directory of the iOS SDK with which to launch the simulator.
     */
    public static final String SDK_ROOT_PROPERTY_NAME = "victor.sdk.root";

    /**
     * Specifies the path to the iOS Simulator executable file.
     */
    public static final String SIMULATOR_BINARY_PATH_PROPERTY_NAME = "victor.simulator.binary.path";

    private final Map<String,String> settings = new HashMap<String, String>();

    /**
     * Create an {@code IosDeviceConfiguration} with no settings defined.
     */
    public IosDeviceConfiguration() {}

    /**
     * Create an {@code IosDeviceConfiguration} with settings copied from the given map;
     * @param settings a map that defines settings.
     */
    public IosDeviceConfiguration(Map<String, String> settings) {
        this.settings.putAll(settings);
    }

    public String getApplicationBinaryPath() {
        return settings.get(APPLICATION_BINARY_PATH_PROPERTY_NAME);
    }

    public void setApplicationBinaryPath(String applicationBinaryPath) {
        settings.put(APPLICATION_BINARY_PATH_PROPERTY_NAME, applicationBinaryPath);
    }

    public String getSdkRoot() {
        return settings.get(SDK_ROOT_PROPERTY_NAME);
    }

    public void setSdkRoot(String sdkRoot) {
        settings.put(SDK_ROOT_PROPERTY_NAME, sdkRoot);
    }

    public String getSimulatorBinaryPath() {
        return settings.get(SIMULATOR_BINARY_PATH_PROPERTY_NAME);
    }

    public void setSimulatorBinaryPath(String simulatorBinaryPath) {
        settings.put(SIMULATOR_BINARY_PATH_PROPERTY_NAME, simulatorBinaryPath);
    }
}
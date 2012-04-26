package com.dhemery.victor.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.dhemery.victor.device.IosDeviceConfigurationProperties.*;

/**
 * <p>Configuration options used by {@link CreateIosDevice} to create devices.</p>
 */
public class IosDeviceConfiguration {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Map<String, String> properties = new HashMap<String, String>();

    /**
     * Create an {@code IosDeviceConfiguration} with no properties defined.
     */
    public IosDeviceConfiguration() {
    }

    /**
     * Create an {@code IosDeviceConfiguration} with properties copied from the given map;
     * Relevant configuration properties are described in {@link }IosDeviceConfigurationProperties}.
     *
     * @param properties a map that defines properties.
     */
    public IosDeviceConfiguration(Map<String, String> properties) {
        this.properties.putAll(properties);
    }

    public String getApplicationBinaryPath() {
        return properties.get(APPLICATION_BINARY_PATH);
    }

    public void setApplicationBinaryPath(String applicationBinaryPath) {
        properties.put(APPLICATION_BINARY_PATH, applicationBinaryPath);
    }

    public String getSdkRoot() {
        return properties.get(SDK_ROOT);
    }

    public void setSdkRoot(String sdkRoot) {
        properties.put(SDK_ROOT, sdkRoot);
    }

    public String getSimulatorBinaryPath() {
        return properties.get(SIMULATOR_BINARY_PATH);
    }

    public void setSimulatorBinaryPath(String simulatorBinaryPath) {
        properties.put(SIMULATOR_BINARY_PATH, simulatorBinaryPath);
    }
}
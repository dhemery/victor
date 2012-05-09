package com.dhemery.victor.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.dhemery.victor.device.IosDeviceConfigurationProperties.*;

/**
 * <p>Configuration options used by {@link CreateIosDevice} to create devices.</p>
 */
// todo Reduce this class to valueOf() and set().
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

    public String applicationBinaryPath() {
        return valueOf(APPLICATION_BINARY_PATH);
    }

    public String deviceType() {
        return valueOf(DEVICE_TYPE);
    }

    public String sdkRoot() {
        return valueOf(SDK_ROOT);
    }

    public String simulatorBinaryPath() {
        return valueOf(SIMULATOR_BINARY_PATH);
    }

    public String simulatorProcessOwner() {
        return valueOf(SIMULATOR_PROCESS_OWNER);
    }

    public void setApplicationBinaryPath(String applicationBinaryPath) {
        set(APPLICATION_BINARY_PATH, applicationBinaryPath);
    }

    public void setDeviceType(String deviceType) {
        set(DEVICE_TYPE, deviceType);
    }

    public void setSdkRoot(String sdkRoot) {
        set(SDK_ROOT, sdkRoot);
    }

    public void setSimulatorBinaryPath(String simulatorBinaryPath) {
        set(SIMULATOR_BINARY_PATH, simulatorBinaryPath);
    }

    public void setSimulatorProcessOwner(String simulatorProcessOwner) {
        set(SIMULATOR_PROCESS_OWNER, simulatorProcessOwner);
    }

    public void set(String property, String value) {
        properties.put(property, value);
    }

    public String valueOf(String property) {
        return properties.get(property);
    }
}
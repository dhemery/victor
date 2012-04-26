package com.dhemery.victor.device;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class IosDeviceCapabilities {
    /**
     * Specifies path to the iOS application binary file to execute.
     * This is typically a file inside the application's .app package.
     * The file's executable flag must be set.
     */
    public static final String APPLICATION_BINARY_PATH_PROPERTY = "victor.application.binary.path";

    /**
     * The value used for {@link #SDK_ROOT_PROPERTY} if the user does not supply a value.
     * The value is calculated based on the values of
     * {@link #DEVELOPER_ROOT_PROPERTY} and {@link #SDK_VERSION_PROPERTY}.
     */
    public static final String DEFAULT_SDK_ROOT_FOR_DEVELOPER_ROOT_AND_SDK_VERSION = "%s/Platforms/iPhoneSimulator.platform/Developer/SDKs/iPhoneSimulator%s.sdk";

    /**
     * The value used for {@link #SDK_VERSION_PROPERTY} if the user does not supply a value.
     */
    public static final String DEFAULT_SDK_VERSION = "5.1";

    /**
     * The value used for {@link #SIMULATOR_BINARY_PATH_PROPERTY} if the user does not suppy a value.
     * The value is calculated based on the value of {@link #DEVELOPER_ROOT_PROPERTY}.
     */
    public static final String DEFAULT_SIMULATOR_BINARY_PATH_FOR_DEVELOPER_ROOT = "%s/Platforms/iPhoneSimulator.platform/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator";

    /**
     * Specifies the path to the Xcode developer root directory.
     * If the user does nto supply a value,
     * Victor obtains the value by calling {@code xcode-select -print-path}.
     */
    public static final String DEVELOPER_ROOT_PROPERTY = "victor.developer.root";

    /**
     * Specifies the path to the root directory of the iOS SDK with which to launch the simulator.
     */
    public static final String SDK_ROOT_PROPERTY = "victor.sdk.root";

    /**
     * Specifies the version of the iOS SDK with which to launch the simulator.
     * This value is unnecessary (and ignored) if the user specifies a value for {@link #SDK_ROOT_PROPERTY}.
     */
    public static final String SDK_VERSION_PROPERTY = "victor.sdk.version";

    /**
     * Specifies the path to the iOS Simulator executable file.
     */
    public static final String SIMULATOR_BINARY_PATH_PROPERTY = "victor.simulator.binary.path";

    private final Map<String,String> capabilities = new HashMap<String, String>();

    /**
     * Create an {@code IosDeviceCapabilities} with default values for all properties.
     */
    public IosDeviceCapabilities() {
        this(new Properties());
    }

    /**
     * Create an {@code IosDeviceCapabilities} with properties copied from the given {@link Properties} object;
     * @param properties
     */
    public IosDeviceCapabilities(Properties properties) {
        setDefaultValues();
        copyVictorProperties(properties);
    }

    public String applicationBinaryPath() {
        return capabilities.get(APPLICATION_BINARY_PATH_PROPERTY);
    }

    public String developerRoot() {
        return capabilities.get(DEVELOPER_ROOT_PROPERTY);
    }

    public String sdkRoot() {
        return capability(SDK_ROOT_PROPERTY, defaultSdkRoot());
    }

    public String sdkVersion() {
        return capabilities.get(SDK_VERSION_PROPERTY);
    }

    public String simulatorBinaryPath() {
        return capability(SIMULATOR_BINARY_PATH_PROPERTY, defaultSimulatorBinaryPath());
    }

    private String capability(String capabilityName, String defaultValue) {
        if(capabilities.containsKey(capabilityName)) return capabilities.get(capabilityName);
        return defaultValue;
    }

    public String defaultDeveloperRoot() {
        try {
            Process process = new ProcessBuilder().command("xcode-select", "-print-path").start();
            return outputFromProcess(process);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // todo Use SDKROOT if specified.
    private String defaultSdkRoot() {
        return String.format(DEFAULT_SDK_ROOT_FOR_DEVELOPER_ROOT_AND_SDK_VERSION, developerRoot(), sdkVersion());
    }

    // todo Default to the latest installed SDK.
    public String defaultSdkVersion() {
        return DEFAULT_SDK_VERSION;
    }

    public String defaultSimulatorBinaryPath() {
        return String.format(DEFAULT_SIMULATOR_BINARY_PATH_FOR_DEVELOPER_ROOT, developerRoot());
    }

    /**
     * @param simulatorBinaryPath the value for {@link #SIMULATOR_BINARY_PATH_PROPERTY}.
     */
    public void setSimulatorBinaryPath(String simulatorBinaryPath) {
        capabilities.put(SIMULATOR_BINARY_PATH_PROPERTY, simulatorBinaryPath);
    }

    /**
     * @param developerRoot the value for {@link #DEVELOPER_ROOT_PROPERTY}.
     */
    public void setDeveloperRoot(String developerRoot) {
        capabilities.put(DEVELOPER_ROOT_PROPERTY, developerRoot);
    }

    /**
     * @param sdkVersion the value for {@link #SDK_VERSION_PROPERTY}.
     */
    public void setSdkVersion(String sdkVersion) {
        capabilities.put(SDK_VERSION_PROPERTY, sdkVersion);
    }

    /**
     * @param sdkRoot the value for {@link #SDK_ROOT_PROPERTY}.
     */
    public void setSdkRoot(String sdkRoot) {
        capabilities.put(SDK_ROOT_PROPERTY, sdkRoot);
    }

    private void copyVictorProperties(Properties properties) {
        for(String name : properties.stringPropertyNames()) {
            if(name.startsWith("victor.")) {
                capabilities.put(name, properties.getProperty(name));
            }
        }
    }

    private void setDefaultValues() {
        capabilities.put(DEVELOPER_ROOT_PROPERTY, defaultDeveloperRoot());
        capabilities.put(SDK_VERSION_PROPERTY, defaultSdkVersion());
    }

    private String outputFromProcess(Process process) throws IOException {
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader.readLine();
    }
}
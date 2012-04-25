package com.dhemery.victor.device;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class IosDeviceCapabilities {
    public static final String APPLICATION_BINARY_PATH = "victor.application.binary.path";
    public static final String DEFAULT_SDK_ROOT_FOR_DEVELOPER_ROOT_AND_SDK_VERSION = "%s/Platforms/iPhoneSimulator.platform/Developer/SDKs/iPhoneSimulator%s.sdk";
    public static final String DEFAULT_SDK_VERSION = "5.1";
    public static final String DEFAULT_SIMULATOR_BINARY_PATH_FOR_DEVELOPER_ROOT = "%s/Platforms/iPhoneSimulator.platform/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator";
    public static final String DEVELOPER_ROOT_PROPERTY = "victor.developer.root";
    public static final String SDK_ROOT = "victor.sdk.root";
    public static final String SDK_VERSION = "victor.sdk.version";
    public static final String SIMULATOR_BINARY_PATH = "victor.simulator.binary.path";
    protected final Map<String,String> capabilities = new HashMap<String, String>();

    public IosDeviceCapabilities() {
        this(new Properties());
    }

    public IosDeviceCapabilities(Properties properties) {
        setDefaultValues();
        copyVictorProperties(properties);
    }

    public String applicationBinaryPath() {
        return capabilities.get(APPLICATION_BINARY_PATH);
    }

    public String developerRoot() {
        return capabilities.get(DEVELOPER_ROOT_PROPERTY);
    }

    public String sdkRoot() {
        return capability(SDK_ROOT, defaultSdkRoot());
    }

    public String sdkVersion() {
        return capabilities.get(SDK_VERSION);
    }

    public String simulatorBinaryPath() {
        return capability(SIMULATOR_BINARY_PATH, defaultSimulatorBinaryPath());
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

    public void setSimulatorBinaryPath(String simulatorBinaryPath) {
        capabilities.put(SIMULATOR_BINARY_PATH, simulatorBinaryPath);
    }

    public void setDeveloperRoot(String developerRoot) {
        capabilities.put(DEVELOPER_ROOT_PROPERTY, developerRoot);
    }

    public void setSdkVersion(String sdkVersion) {
        capabilities.put(SDK_VERSION, sdkVersion);
    }

    public void setSdkRoot(String sdkRoot) {
        capabilities.put(SDK_ROOT, sdkRoot);
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
        capabilities.put(SDK_VERSION, defaultSdkVersion());
    }

    private String outputFromProcess(Process process) throws IOException {
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader.readLine();
    }
}
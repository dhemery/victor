package com.dhemery.victor.device;

import com.dhemery.victor.IosDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateIosDevice {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final IosDeviceConfiguration configuration;
    private String developerRoot;

    public static IosDevice withCapabilities(IosDeviceConfiguration configuration) {
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
        if(applicationBinaryPath != null) return applicationBinaryPath;
        String explanation = String.format("Configuration option %s not defined", IosDeviceConfiguration.APPLICATION_BINARY_PATH);
        throw new IosDeviceConfigurationException(explanation);
    }

    private String simulatorBinaryPath() {
        String simulatorBinaryPath = configuration.getSimulatorBinaryPath();
        if(simulatorBinaryPath != null) return simulatorBinaryPath;
        simulatorBinaryPath = defaultSimulatorBinaryPath();
        log.trace("Configuration option {} not defined. Using default value {}", IosDeviceConfiguration.SIMULATOR_BINARY_PATH, simulatorBinaryPath);
        return simulatorBinaryPath;
    }

    public String defaultSimulatorBinaryPath() {
        return String.format(DEFAULT_SIMULATOR_BINARY_PATH_FOR_DEVELOPER_ROOT, developerRoot());
    }

    private String sdkRoot() {
        String sdkRoot = configuration.getSdkRoot();
        if(sdkRoot != null) return sdkRoot;
        sdkRoot = defaultSdkRoot();
        log.trace("Configuration option {} not defined. Using default value {}", IosDeviceConfiguration.SDK_ROOT, sdkRoot);
        return sdkRoot;
    }

    private String defaultSdkRoot() {
        String sdkRootsPath = String.format(SDK_ROOTS_PATH_FOR_DEVELOPER_ROOT, developerRoot());
        List<File> sdks = Arrays.asList(new File(sdkRootsPath).listFiles());
        if(sdks.isEmpty()) throw new IosDeviceConfigurationException(String.format("Configuration option %s not defined, and no SDKs found in {}", IosDeviceConfiguration.SDK_ROOT, sdkRootsPath));
        return latestSdkIn(sdks);
    }

    private String latestSdkIn(List<File> sdks) {
        Collections.sort(sdks);
        return sdks.get(sdks.size()-1).getAbsolutePath();
    }

    public String developerRoot() {
        if(developerRoot != null) return developerRoot;
        try {
            Process xcodeSelect = new ProcessBuilder().command("xcode-select", "-print-path").start();
            developerRoot = outputFromProcess(xcodeSelect);
            return developerRoot;
        } catch (IOException cause) {
            throw new IosDeviceConfigurationException("Error running xcode-select to discover developer root", cause);
        }
    }

    private String outputFromProcess(Process process) throws IOException {
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader.readLine();
    }



    public static final String SDK_ROOTS_PATH_FOR_DEVELOPER_ROOT = "%s/Platforms/iPhoneSimulator.platform/Developer/SDKs";
    public static final String DEFAULT_SDK_ROOT_FOR_DEVELOPER_ROOT_AND_SDK_VERSION = SDK_ROOTS_PATH_FOR_DEVELOPER_ROOT + "/iPhoneSimulator%s.sdk";
    public static final String DEFAULT_SIMULATOR_BINARY_PATH_FOR_DEVELOPER_ROOT = "%s/Platforms/iPhoneSimulator.platform/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator";
}

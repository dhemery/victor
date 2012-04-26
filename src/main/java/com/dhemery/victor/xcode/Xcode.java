package com.dhemery.victor.xcode;

import com.dhemery.victor.device.IosDeviceConfigurationException;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Utility methods to obtain information about the Xcode development environment.
 */
public class Xcode {
    /**
     * The default path from {@link #developerRoot() developer root} to the Simulator binary.
     */
    public static final String DEFAULT_SIMULATOR_BINARY_PATH_FOR_DEVELOPER_ROOT = "%s/Platforms/iPhoneSimulator.platform/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator";

    /**
     * The default path from {@link #developerRoot() developer root} to the directory where iPhoneSimulator SDKs are installed.
     */
    public static String DEFAULT_SDK_ROOTS_PATH_FOR_DEVELOPER_ROOT = "%s/Platforms/iPhoneSimulator.platform/Developer/SDKs";

    private String developerRoot;

    /**
     * Determine the current Xcode developer root
     * by running Run {@code xcode-select -print-path}.
     *
     * @return the current developer root.
     */
    public String developerRoot() {
        if (developerRoot != null) return developerRoot;
        try {
            Process xcodeSelect = new ProcessBuilder().command("xcode-select", "-print-path").start();
            return outputFromProcess(xcodeSelect);
        } catch (IOException cause) {
            throw new IosDeviceConfigurationException("Error running xcode-select to discover developer root", cause);
        }
    }

    private boolean empty(File[] fileArray) {
        return (fileArray == null) || (fileArray.length < 1);
    }

    private File lexicographicallyLastFileIn(File[] files) {
        List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList);
        return fileList.get(fileList.size() - 1);
    }

    /**
     * <p>Determine the root path of the newest iPhoneSimulator SDK installed in the Xcode development environment.
     * This method looks for SDKs in {@link #DEFAULT_SDK_ROOTS_PATH_FOR_DEVELOPER_ROOT a fixed location}
     * below {@link #developerRoot() developer root}.</p>
     * <p/>
     * <p><strong>WARNING.</strong> This method:</p>
     * <ul>
     * <li><strong>Assumes</strong> that iPhoneSimulator SDK roots are installed in the current developer environment
     * in a fixed location.
     * See the value of {@link #DEFAULT_SDK_ROOTS_PATH_FOR_DEVELOPER_ROOT}.
     * </li>
     * <li><strong>Assumes</strong> that the fixed location contains nothing but iOS SDKs.</li>
     * </ul>
     *
     * @return the root path to the newest iPhoneSimulator SDK in the current Xcode development environment.
     */
    public String newestSdkRoot() {
        String sdkRootsPath = String.format(DEFAULT_SDK_ROOTS_PATH_FOR_DEVELOPER_ROOT, developerRoot());
        File[] sdks = new File(sdkRootsPath).listFiles();
        if (empty(sdks)) {
            throw new IosDeviceConfigurationException(String.format("Configuration option %s not defined, and no SDKs found in %s", DEFAULT_SDK_ROOTS_PATH_FOR_DEVELOPER_ROOT, sdkRootsPath));
        }
        return lexicographicallyLastFileIn(sdks).getAbsolutePath();
    }

    private String outputFromProcess(Process process) throws IOException {
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader.readLine();
    }

    /**
     * <p>Determine the default path to the iPhoneSimulator Simulator executable for the Xcode development environment.
     * This method looks for the simulator executable
     * in {@link #DEFAULT_SIMULATOR_BINARY_PATH_FOR_DEVELOPER_ROOT a fixed location}
     * below {@link #developerRoot() developer root}.</p>
     * <p/>
     * <p><strong>WARNING.</strong> This method:</p>
     * <ul>
     * <li><strong>Assumes</strong> that the simulator is installed in the current developer environment
     * in a fixed location.
     * <li><strong>Does not</strong> determine whether a file exists at that location,
     * or that the file is executable.</li>
     * </ul>
     *
     * @return the default path to the iPhoneSimulator Simulator executable for the current Xcode development environment.
     */
    public String simulatorBinaryPath() {
        return String.format(DEFAULT_SIMULATOR_BINARY_PATH_FOR_DEVELOPER_ROOT, developerRoot());
    }
}

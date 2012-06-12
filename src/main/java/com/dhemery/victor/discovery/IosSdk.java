package com.dhemery.victor.discovery;

import com.dhemery.configuration.ContextItemCache;

/**
 * Represents an iOS SDK.
 */
public class IosSdk {
    public static final String GENERIC_SDK_NAME = "iphonesimulator";
    public static final String NAME_FOR_SDK_VERSION = GENERIC_SDK_NAME + "%s";
    public static final String PLATFORM_PATH = "PlatformPath";
    public static final String SDK_PATH = "Path";
    public static final String SDK_VERSION = "SDKVersion";
    public static final String SIMULATOR_BINARY_PATH_FOR_PLATFORM = "%s/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator";
    private static final ContextItemCache sdkInfo = new SdkInfoCache();
    protected final String canonicalName;

    public IosSdk(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    /**
     * @return a representation of the newest installed SDK.
     */
    public static IosSdk newest() {
        return withCanonicalName(GENERIC_SDK_NAME);
    }

    /**
     * @param canonicalName the canonical name of an SDK.
     * @return a representation of the SDK with that canonical name.
     */
    public static IosSdk withCanonicalName(String canonicalName) {
        return new IosSdk(canonicalName);
    }

    /**
     * @param version the version of an SDK.
     * @return a representation of the SDK with that version.
     */
    public static IosSdk withVersion(String version) {
        return withCanonicalName(String.format(NAME_FOR_SDK_VERSION, version));
    }

    public String canonicalName() {
        return canonicalName;
    }

    public boolean isInstalled() {
        return path() != null;
    }

    public String sdkInfo(String itemName) {
        return sdkInfo(canonicalName, itemName);
    }

    public String path(){
        return sdkInfo(SDK_PATH);
    }

    public String version() {
        return sdkInfo(SDK_VERSION);
    }

    /**
     * @return the absolute path to the iPhone Simulator platform on this computer.
     */
    public static String platformPath() {
        return sdkInfo(GENERIC_SDK_NAME, PLATFORM_PATH);
    }

    private static String sdkInfo(String canonicalName, String itemName) {
        return sdkInfo.value(canonicalName, itemName);
    }

    /**
     * <p>Compute the path to the iPhoneSimulator Simulator executable
     * in the current the Xcode development environment.
     * <p/>
     * <p><strong>WARNING.</strong> This method:</p>
     * <ul>
     * <li><strong>Assumes</strong> that the simulator executable is located
     * at {@link #SIMULATOR_BINARY_PATH_FOR_PLATFORM a fixed location}
     * within {@link #platformPath() the iPhone Simulator platform}.</li>
     * <li><strong>Does not</strong> determine whether a file exists at that location,
     * or that the file is executable.</li>
     * </ul>
     *
     * @return the absolute path to the iPhoneSimulator Simulator executable.
     */
    public static String simulatorBinaryPath() {
        return String.format(SIMULATOR_BINARY_PATH_FOR_PLATFORM, platformPath());
    }
}

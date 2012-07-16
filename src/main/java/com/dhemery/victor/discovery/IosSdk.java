package com.dhemery.victor.discovery;

import com.dhemery.configuration.SingleSourceMappedCache;

/**
 * Represents an iOS SDK in the currently active Xcode development environment.
 * The active development environment is the one reported
 * by {@code xcode-select -print-path}.
 */
public class IosSdk {
    private final SingleSourceMappedCache<SdkItemKey,String> sdkInfo;
    public static final String GENERIC_SDK_NAME = "iphonesimulator";
    public static final String NAME_FOR_SDK_VERSION = GENERIC_SDK_NAME + "%s";
    public static final String PLATFORM_PATH = "PlatformPath";
    public static final String SDK_PATH = "Path";
    public static final String SDK_VERSION = "SDKVersion";
    public static final String SIMULATOR_BINARY_PATH_FOR_PLATFORM = "%s/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator";
    private final String canonicalName;

    /**
     * Create a representation of an iOS SDK.
     * @param sdkInfo a source of information about iOS SDKs installed on this computer.
     * @param canonicalName the canonical name of the iOS SDK to represent.
     */
    public IosSdk(SingleSourceMappedCache<SdkItemKey,String> sdkInfo, String canonicalName) {
        this.sdkInfo = sdkInfo;
        this.canonicalName = canonicalName;
    }

    /**
     * A representation of the newest iOS SDK installed in the active environment.
     */
    public static IosSdk newest(SingleSourceMappedCache<SdkItemKey,String> sdkInfo) {
        return withCanonicalName(sdkInfo, GENERIC_SDK_NAME);
    }

    /**
     * A representation of the iOS SDK with a given canonical name.
     * @param canonicalName the canonical name of an iOS SDK.
     */
    public static IosSdk withCanonicalName(SingleSourceMappedCache<SdkItemKey,String> sdkInfo, String canonicalName) {
        return new IosSdk(sdkInfo, canonicalName);
    }

    /**
     * A representation of the iOS SDK with a given version.
     * @param version the version of an iOS SDK.
     */
    public static IosSdk withVersion(SingleSourceMappedCache<SdkItemKey,String> sdkInfo, String version) {
        return withCanonicalName(sdkInfo, String.format(NAME_FOR_SDK_VERSION, version));
    }

    /**
     * This iOS SDK's canonical name.
     */
    public String canonicalName() {
        return canonicalName;
    }

    /**
     * Report this iOS SDK is installed in the active development environment.
     */
    public boolean isInstalled() {
        return path() != null;
    }

    /**
     * Return an information item for this iOS SDK.
     * @param itemName the name of the item to retrieve.
     */
    public String sdkInfo(String itemName) {
        return sdkInfo(canonicalName, itemName);
    }

    /**
     * The absolute path to this iOS SDK.
     */
    public String path(){
        return sdkInfo(SDK_PATH);
    }

    /**
     * This iOS SDK's version.
     */
    public String version() {
        return sdkInfo(SDK_VERSION);
    }

    /**
     * The absolute path to the iPhone Simulator platform in the active development environment.
     */
    public String platformPath() {
        return sdkInfo(GENERIC_SDK_NAME, PLATFORM_PATH);
    }

    private String sdkInfo(String canonicalName, String itemName) {
        return sdkInfo.value(new SdkItemKey(canonicalName, itemName));
    }

    /**
     * <p>Compute the path to the iPhoneSimulator Simulator executable
     * in the active development environment.
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
     * @return the absolute path to the iPhoneSimulator Simulator executable in the active development environment.
     */
    public String simulatorBinaryPath() {
        return String.format(SIMULATOR_BINARY_PATH_FOR_PLATFORM, platformPath());
    }
}

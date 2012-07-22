package com.dhemery.victor.discovery;

import com.dhemery.configuration.ConfigurationException;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;

/**
 * Represents an iOS SDK in the currently active Xcode development environment.
 * The active development environment is the one reported
 * by {@code xcode-select -print-path}.
 */
public class IosSdk {
    public static final String GENERIC_SDK_NAME = "iphonesimulator";
    public static final String NAME_FOR_SDK_VERSION = GENERIC_SDK_NAME + "%s";
    public static final String PLATFORM_PATH = "PlatformPath";
    public static final String SDK_PATH = "Path";
    public static final String SDK_VERSION = "SDKVersion";
    public static final String SIMULATOR_BINARY_PATH_FOR_PLATFORM = "%s/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator";
    private final LoadingCache<SdkItem,String> sdkInfo;
    private final String canonicalName;

    /**
     * Create a representation of an iOS SDK.
     * @param canonicalName the canonical name of the iOS SDK to represent.
     */
    public IosSdk(String canonicalName, LoadingCache<SdkItem,String> sdkInfo) {
        this.canonicalName = canonicalName;
        this.sdkInfo = sdkInfo;
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
     * The absolute path to the iPhone Simulator platform in the active development environment.
     */
    public String platformPath() {
        return sdkInfo(GENERIC_SDK_NAME, PLATFORM_PATH);
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

    /**
     * This iOS SDK's version.
     */
    public String version() {
        return sdkInfo(SDK_VERSION);
    }

    private String sdkInfo(String canonicalName, String itemName) {
        try {
            return sdkInfo.get(new SdkItem(canonicalName, itemName));
        } catch (ExecutionException cause) {
            String explanation = String.format("Cannot retrieve item %s for SDK %s", itemName, canonicalName);
            throw new ConfigurationException(explanation, cause);
        }
    }
}

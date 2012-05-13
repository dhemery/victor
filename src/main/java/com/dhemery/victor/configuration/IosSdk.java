package com.dhemery.victor.configuration;

import com.dhemery.victor.configuration.generic.ContextItemCache;

public class IosSdk {
    public static final String GENERIC_SDK_NAME = "iphonesimulator";
    public static final String NAME_FOR_SDK_VERSION = GENERIC_SDK_NAME + "%s";
    public static final String PLATFORM_PATH = "PlatformPath";
    public static final String SDK_PATH = "Path";
    public static final String SDK_VERSION = "SDKVersion";
    public static final String SIMULATOR_BINARY_PATH_FOR_PLATFORM = "%s/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator";

    private static final ContextItemCache sdkInfo = new SdkInfoCache();

    private final String canonicalName;

    /**
     * @return a representation of the newest installed SDK.
     */
    public static IosSdk newest() {
        return IosSdk.withCanonicalName(GENERIC_SDK_NAME);
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

    private IosSdk(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    /**
     * @return the canonical name of this SDK.
     */
    public String canonicalName() {
        return String.format(NAME_FOR_SDK_VERSION, version());
    }

    /**
     * @return whether the SDK is installed on this computer.
     */
    public boolean isInstalled() {
        return sdkInfo(SDK_PATH).startsWith("/");
    }

    /**
     * <p></p>Obtain information about the SDK.</p>
     * <p>
     * To learn the list of available infoitems,
     * see the {@code xcodebuild} manual page,
     * especially the description of the {@code -version} option.
     * For examples of infoitems and values,
     * run {@code xcodebuild -sdk iphonesimulator -version}
     * on the command line.
     * </p>
     * <p>
     * To learn the list of installed SDKs and their canonical names,
     * run {@code xcodebuild -showsdks} on the command line.
     * </p>
     * @param itemName the name of an infoitem
     * @return the value of the infoitem for the named SDK
     */
    public String sdkInfo(String itemName) {
        return sdkInfo(canonicalName, itemName);
    }

    private static String sdkInfo(String canonicalName, String itemName) {
        return sdkInfo.value(canonicalName, itemName);
    }

    /**
     * @return the absolute path to this SDK.
     */
    public String path() {
        return sdkInfo(SDK_PATH);
    }

    /**
     * @return the absolute path to the iPhone Simulator platform on this computer.
     */
    public static String platformPath() {
        return sdkInfo(GENERIC_SDK_NAME, PLATFORM_PATH);
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

    /**
     * @return the version of this SDK.
     */
    public String version() {
        return sdkInfo(SDK_VERSION);
    }

}

package com.dhemery.victor.configuration;

import com.dhemery.victor.device.local.OSCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Utility methods that describe the {@code Xcode} development environment.
 */
public class XcodeBuild {
    public static final String CANONICAL_NAME_FOR_SDK_VERSION = "iphonesimulator%s";
    public static final String CANONICAL_NAME_FOR_NEWEST_SDK = String.format(CANONICAL_NAME_FOR_SDK_VERSION, "");
    public static final String PLATFORM_PATH = "PlatformPath";
    public static final String SDK_PATH = "Path";
    public static final String SDK_VERSION = "SDKVersion";
    public static final String SIMULATOR_BINARY_PATH_FOR_PLATFORM = "%s/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator";

    private XcodeBuild(){}

    public static String canonicalNameForSdkVersion(String sdkVersion) {
        return String.format(CANONICAL_NAME_FOR_SDK_VERSION, sdkVersion);
    }

    /**
     * @return the version of the newest installed iPhone Simulator SDK
     */
    public static String newestInstalledSdkVersion() {
        return infoItem(SDK_VERSION);
    }

    /**
     * @return the absolute path to the iPhone Simulator platform
     */
    public static String platformPath() {
        return infoItem(PLATFORM_PATH);
    }

    /**
     * @param itemName the name of an SDK infoitem
     * @return the value of an infoitem for the newest installed SDK
     * @see #infoItem(String, String)
     */
    public static String infoItem(String itemName) {
        return infoItem(itemName, CANONICAL_NAME_FOR_NEWEST_SDK);
    }

    /**
     * <p></p>Obtain the value of an infoitem for an installed SDK.</p>
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
     * @param sdkCanonicalName the canonical name of an installed SDK
     * @return the value of the infoitem for the named SDK
     */
    public static String infoItem(String itemName, String sdkCanonicalName) {
        List<String> arguments = Arrays.asList("-sdk", sdkCanonicalName, "-version", itemName);
        OSCommand command = new OSCommand("xcodebuild", arguments);
        return command.output();
    }

    /**
     * @param sdkVersion the version of an installed SDK
     * @return the absolute path to the SDK
     */
    public static String sdkPathForVersion(String sdkVersion) {
        return infoItem(SDK_PATH, canonicalNameForSdkVersion(sdkVersion));
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
     * @return the absolute path to the iPhoneSimulator Simulator executable
     */
    public static String simulatorBinaryPath() {
        return String.format(SIMULATOR_BINARY_PATH_FOR_PLATFORM, platformPath());
    }
}

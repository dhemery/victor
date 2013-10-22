package com.dhemery.victor;

public interface IosSdk {
    /**
     * This iOS SDK's canonical name.
     */
    String canonicalName();

    /**
     * Report whether this iOS SDK is installed in the active development environment.
     */
    boolean isInstalled();

    /**
     * Return an information item for this iOS SDK.
     * @param itemName the name of the item to retrieve.
     */
    String sdkInfo(String itemName);

    /**
     * The absolute path to this iOS SDK.
     */
    String path();

    /**
     * The absolute path to the iPhone Simulator platform in the active development environment.
     */
    String platformPath();

    /**
     * The path to the iPhoneSimulator Simulator executable
     * in the active development environment.
     * <p>
     * Note that each implementation may or may not verify that
     * the simulator binary actually exists
     * or that it is executable.
     */
    String simulatorBinaryPath();

    /**
     * This iOS SDK's version.
     */
    String version();
}

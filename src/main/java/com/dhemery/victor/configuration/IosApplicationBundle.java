package com.dhemery.victor.configuration;

import com.dhemery.configuration.ContextItemCache;

import java.io.File;

/**
 * Represents an application bundle.
 */
public class IosApplicationBundle {
    private static final ContextItemCache defaults = new DefaultsCache();
    public static final String SDK_CANONICAL_NAME = "DTSDKName";
    public static final String EXECUTABLE_NAME = "CFBundleExecutable";

    private final String path;
    private final String plistPath;

    /**
     * @param path the absolute path to the application bundle.
     */
    public IosApplicationBundle(String path) {
        this.path = path;
        plistPath = path + "/Info";
    }

    /**
     * @return whether the bundle's Info.plist file identifies an SDK.
     */
    public boolean identifiesAnSdk() {
        return sdkCanonicalName().startsWith(IosSdk.GENERIC_SDK_NAME);
    }

    /**
     * @return the name of the executable file in the bundle.
     */
    private String executableName() {
        return applicationInfo(EXECUTABLE_NAME);
    }

    private String applicationInfo(String item) {
        return defaults.value(plistPath, item);
    }

    /**
     * @return whether Victor can execute the bundle's executable file.
     */
    public boolean isExecutable() {
        return new File(pathToExecutable()).canExecute();
    }

    /**
     * @return the absolute path to the bundle's executable file.
     */
    public String pathToExecutable() {
        return String.format("%s/%s", path, executableName());
    }

    /**
     * Returns the SDK identified in the bundle's Info.plist file.
     * If the plist file does not identify an SDK,
     * the return value is undefined.
     * @return the SDK identified in the bundle's Info.plist file.
     * @see #identifiesAnSdk()
     */
    public String sdkCanonicalName() {
        return applicationInfo(SDK_CANONICAL_NAME);
    }
}

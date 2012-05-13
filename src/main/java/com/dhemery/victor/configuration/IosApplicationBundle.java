package com.dhemery.victor.configuration;

import com.dhemery.victor.configuration.generic.ContextItemCache;

import java.io.File;

public class IosApplicationBundle {
    private static final ContextItemCache defaults = new DefaultsCache();
    public static final String SDK_CANONICAL_NAME = "DTSDKName";
    public static final String EXECUTABLE_NAME = "CFBundleExecutable";

    private final String path;
    private final String plistPath;

    public IosApplicationBundle(String path) {
        this.path = path;
        plistPath = path + "/Info";
    }

    public boolean definesSdkCanonicalName() {
        return sdkCanonicalName().startsWith(IosSdk.GENERIC_SDK_NAME);
    }

    private String executableName() {
        return applicationInfo(EXECUTABLE_NAME);
    }

    private String applicationInfo(String item) {
        return defaults.value(plistPath, item);
    }

    public boolean isExecutable() {
        return new File(pathToExecutable()).canExecute();
    }

    public String pathToExecutable() {
        return String.format("%s/%s", path, executableName());
    }

    public String sdkCanonicalName() {
        return applicationInfo(SDK_CANONICAL_NAME);
    }
}

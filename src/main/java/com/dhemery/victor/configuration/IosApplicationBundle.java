package com.dhemery.victor.configuration;

import java.io.File;

public class IosApplicationBundle {
    public static final String SDK_CANONICAL_NAME = "DTSDKName";
    public static final String EXECUTABLE_NAME = "CFBundleExecutable";
    private static final Cache applicationInfoCache = new Cache(new DefaultsInspector());
    private final String path;
    private final String plistFilePath;

    public IosApplicationBundle(String path) {
        this.path = path;
        plistFilePath = path + "/Info";
    }

    public boolean definesSdkCanonicalName() {
        return sdkCanonicalName().startsWith(IosSdk.GENERIC_SDK_NAME);
    }

    private String executableName() {
        return applicationInfo(EXECUTABLE_NAME);
    }

    private String applicationInfo(String item) {
        return applicationInfoCache.value(plistFilePath, item);
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

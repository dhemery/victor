package com.dhemery.victor.configuration;

import com.dhemery.victor.os.OSCommand;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class IosApplicationBundle {
    public static final String SDK_CANONICAL_NAME = "DTSDKName";
    public static final String EXECUTABLE_NAME = "CFBundleExecutable";
    private static final ContextItemCache applicationInfo = new ContextItemCache(applicationInfoFetcher());

    private static ContextItemFetcher applicationInfoFetcher() {
        return new ContextItemFetcher() {
            @Override
            public String fetch(String domain, String item) {
                List<String> arguments = Arrays.asList("read", domain, item);
                OSCommand command = new OSCommand("defaults", arguments);
                return command.output();

            }
        };
    }

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
        return applicationInfo.value(plistFilePath, item);
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

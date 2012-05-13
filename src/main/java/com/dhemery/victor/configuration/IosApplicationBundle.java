package com.dhemery.victor.configuration;

import com.dhemery.victor.device.local.OSCommand;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class IosApplicationBundle {
    public static final String SDK_CANONICAL_NAME = "DTSDKName";
    public static final String PATH_TO_EXECUTABLE = "CFBundleExecutable";
    private final String path;

    public IosApplicationBundle(String path) {
        this.path = path;
    }

    public boolean definesSdkCanonicalName() {
        return sdkCanonicalName().startsWith(IosSdk.GENERIC_SDK_NAME);
    }

    private String infoItem(String itemName) {
        List<String> arguments = Arrays.asList(path, itemName);
        OSCommand command = new OSCommand("defaults", arguments);
        return command.output();
    }

    public boolean isExecutable() {
        return new File(pathToExecutable()).canExecute();
    }

    public String pathToExecutable() {
        return infoItem(PATH_TO_EXECUTABLE);
    }

    public String sdkCanonicalName() {
        return infoItem(SDK_CANONICAL_NAME);
    }
}

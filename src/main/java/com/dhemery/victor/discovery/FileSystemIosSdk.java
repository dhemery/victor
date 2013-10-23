package com.dhemery.victor.discovery;

import com.dhemery.os.Command;
import com.dhemery.os.Shell;
import com.dhemery.victor.IosSdk;

import java.util.HashMap;
import java.util.Map;

import static com.dhemery.os.CommandBuilder.command;

/**
 * Represents an iOS SDK in the currently active Xcode development environment.
 * The active development environment is the one reported
 * by {@code xcode-select -print-path}.
 */
public class FileSystemIosSdk implements IosSdk {
    private static final String GENERIC_SDK_NAME = "iphonesimulator";
    private static final String PLATFORM_PATH = "PlatformPath";
    private static final String SDK_PATH = "Path";
    private static final String SDK_VERSION = "SDKVersion";
    private static final String SIMULATOR_BINARY_PATH_FOR_PLATFORM = "%s/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator";
    Map<IosSdkItem,String> items = new HashMap<>();
    private final String canonicalName;
    private final Shell shell;

    /**
     * Create a representation of an iOS SDK.
     * @param canonicalName the canonical name of the iOS SDK to represent
     * @param shell the Shell to use to execute command line commands
     */
    public FileSystemIosSdk(String canonicalName, Shell shell) {
        this.canonicalName = canonicalName;
        this.shell = shell;
    }

    @Override
    public String canonicalName() {
        return canonicalName;
    }

    @Override
    public boolean isInstalled() {
        return path() != null;
    }

    @Override
    public String sdkInfo(String itemName) {
        return sdkInfo(canonicalName, itemName);
    }

    @Override
    public String path(){
        return sdkInfo(SDK_PATH);
    }

    @Override
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
    @Override
    public String simulatorBinaryPath() {
        return String.format(SIMULATOR_BINARY_PATH_FOR_PLATFORM, platformPath());
    }

    @Override
    public String version() {
        return sdkInfo(SDK_VERSION);
    }

    private String sdkInfo(String canonicalName, String itemName) {
        IosSdkItem item = new IosSdkItem(canonicalName, itemName);
        if(!items.containsKey(item)) {
            items.put(item, valueOf(item));
        }
        return items.get(item);
    }

    private String valueOf(IosSdkItem item) {
        return outputFrom(commandToFetch(item));
    }

    private String outputFrom(Command command) {
        return shell.run(command).output();
    }

    private Command commandToFetch(IosSdkItem item) {
        return command("SDK Inspector", "xcodebuild")
                .withArguments("-sdk", item.sdkName())
                .withArguments("-version", item.itemName())
                .build();
    }
}

package com.dhemery.victor.discovery;

import com.dhemery.victor.VictorConfigurationException;
import com.dhemery.victor.os.Shell;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an iOS application bundle.
 */
public class IosApplicationBundle {
    private static final String BUNDLE_VERSION = "CFBundleVersion";
    private static final String BUNDLE_IDENTIFIER = "CFBundleIdentifier";
    private static final String EXECUTABLE_NAME = "CFBundleExecutable";
    private static final String DEVICE_FAMILIES = "UIDeviceFamily";
    private static final String SDK_CANONICAL_NAME = "DTSDKName";

    private final Shell shell;
    private final String path;
    private PListInspector plist;

    /**
     * Create a representation of the iOS application bundle at the specified path.
     * Uses the given shell to run commands to discover information about the bundle.
     * @param path the absolute file path to the application bundle.
     * @param shell the shell used to run commands to discover information from the application bundle.
     */
    public IosApplicationBundle(String path, Shell shell) {
        this.shell = shell;
        this.path = path;
        requireFile(path, "application bundle");

    }

    /**
     * The bundle version from the bundle's Info.plist file.
     */
    public String bundleIdentifier() {
        return plist().stringValue(BUNDLE_IDENTIFIER);
    }

    /**
     * The bundle version from the bundle's Info.plist file.
     */
    public String bundleVersion() {
        return plist().stringValue(BUNDLE_VERSION);
    }

    /**
     * The list of device types on which this application can run.
     */
    public List<String> deviceTypes() {
        Integer count = plist().size(DEVICE_FAMILIES);
        List<String> deviceTypes = new ArrayList<String>(count);
        for(int i = 0 ; i < count ; i++) {
            String familyNumber = plist().stringValue(DEVICE_FAMILIES, i);
            if(familyNumber.equals("1")) deviceTypes.add("iPhone");
            if(familyNumber.equals("2")) deviceTypes.add("iPad");
        }
        return deviceTypes;
    }

    /**
     * The name of the bundle's executable file.
     */
    public String executableName() {
        return plist().stringValue(EXECUTABLE_NAME);
    }

    private void requireFile(String path, String description) {
        File file = new File(path);
        if(!file.exists()) {
            throw new VictorConfigurationException(String.format("Can not find %s at %s", description, path));
        }
    }

    /**
     * Report whether Victor can execute the bundle's executable file.
     * Victor can execute the file if it exists and its "executable" bit is set.
     */
    public boolean isExecutable() {
        return new File(pathToExecutable()).canExecute();
    }

    /**
     * The absolute path to the bundle's executable file.
     */
    public String pathToExecutable() {
        return String.format("%s/%s", path, executableName());
    }

    /**
     * A {@link PListInspector} with the contents of the bundle's Info.plist file.
     */
    public PListInspector plist() {
        if(plist == null) {
            String plistPath = path + "/Info.plist";
            requireFile(plistPath, "Info.plist in application bundle");
            plist = new PListInspector(plistPath, shell);
        }
        return plist;
    }

    /**
     * The canonical name of the bundle's target iOS SDK.
     */
    public String sdkCanonicalName() {
        return plist().stringValue(SDK_CANONICAL_NAME);
    }
}

package com.dhemery.victor.configuration;

import com.dhemery.victor.VictorConfigurationException;
import com.dhemery.victor.os.PList;
import com.dhemery.victor.os.Shell;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class IosApplicationBundle {
    private static final String BUNDLE_VERSION = "CFBundleVersion";
    private static final String BUNDLE_IDENTIFIER = "CFBundleIdentifier";
    private static final String EXECUTABLE_NAME = "CFBundleExecutable";
    private static final String DEVICE_FAMILIES = "UIDeviceFamily";
    private static final String SDK_CANONICAL_NAME = "DTSDKName";

    private final Shell shell;
    private final String path;
    private PList plist;

    public IosApplicationBundle(Shell shell, String path) {
        this.shell = shell;
        this.path = path;
        requireFile(path, "application bundle");

    }
    /**
     * @return the bundle's identifier.
     */
    public String bundleIdentifier() {
        return plist().stringValue(BUNDLE_IDENTIFIER);
    }

    /**
     * @return the bundle's version.
     */
    public String bundleVersion() {
        return plist().stringValue(BUNDLE_VERSION);
    }

    /**
     * @return the list of device types on which this bundle can run.
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
     * @return the name of the executable file in the bundle.
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
     * @return a {@link PList} that represents the bundle's Info.plist file.
     */
    public PList plist() {
        if(plist == null) {
            String plistPath = path + "/Info.plist";
            requireFile(plistPath, "Info.plist in application bundle");
            plist = new PList(shell, plistPath);
        }
        return plist;
    }

    /**
     * @return the SDK canonical name with which the bundle was created.
     */
    public String sdkCanonicalName() {
        return plist().stringValue(SDK_CANONICAL_NAME);
    }
}

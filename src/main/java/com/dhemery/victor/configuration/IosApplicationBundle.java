package com.dhemery.victor.configuration;

import com.dhemery.configuration.ContextItemCache;
import com.dhemery.victor.discovery.DefaultsCache;
import com.dhemery.victor.discovery.PList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class IosApplicationBundle {
    private static final Logger log = LoggerFactory.getLogger(IosApplicationBundle.class);
    private static final ContextItemCache defaults = new DefaultsCache();
    public static final String BUNDLE_VERSION = "CFBundleVersion";
    public static final String BUNDLE_IDENTIFIER = "CFBundleIdentifier";
    public static final String EXECUTABLE_NAME = "CFBundleExecutable";
    private static final String DEVICE_FAMILIES = "UIDeviceFamily";
    public static final String SDK_CANONICAL_NAME = "DTSDKName";

    private final String path;
    private final PList plist;

    public IosApplicationBundle(String path) {
        this.path = path;
        String plistPath = path + "/Info.plist";
        requireFile(path, "application bundle");
        requireFile(plistPath, "Info.plist in application bundle");
        plist = new PList(plistPath);
    }

    /**
     * @return the bundle's identifier.
     */
    public String bundleIdentifier() {
        return plist.stringValue(BUNDLE_IDENTIFIER);
    }

    /**
     * @return the bundle's version.
     */
    public String bundleVersion() {
        return plist.stringValue(BUNDLE_VERSION);
    }

    /**
     * @return the list of device types on which this bundle can run.
     */
    public List<String> deviceTypes() {
        Integer count = plist.size(DEVICE_FAMILIES);
        List<String> deviceTypes = new ArrayList<String>(count);
        for(int i = 0 ; i < count ; i++) {
            String familyNumber = plist.stringValue(DEVICE_FAMILIES, i);
            if(familyNumber.equals("1")) deviceTypes.add("iPhone");
            if(familyNumber.equals("2")) deviceTypes.add("iPad");
        }
        return deviceTypes;
    }

    /**
     * @return the name of the executable file in the bundle.
     */
    public String executableName() {
        return plist.stringValue(EXECUTABLE_NAME);
    }

    private void requireFile(String path, String description) {
        File file = new File(path);
        if(!file.exists()) {
            throw new IosDeviceConfigurationException(String.format("Can not find %s at %s", description, path));
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
        return plist;
    }

    /**
     * @return the SDK canonical name with which the bundle was created.
     */
    public String sdkCanonicalName() {
        return plist.stringValue(SDK_CANONICAL_NAME);
    }
}

package com.dhemery.victor.discovery;

import com.dhemery.mac.ApplicationBundle;
import com.dhemery.os.Shell;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an iOS application bundle.
 */
public class IosApplicationBundle extends ApplicationBundle {
    private static final String DEVICE_FAMILIES = "UIDeviceFamily";
    private static final String SDK_CANONICAL_NAME = "DTSDKName";

    /**
     * @param path the absolute file path to the application bundle.
     * @param shell the shell used to run commands to discover information from the application bundle.
     */
    public IosApplicationBundle(String path, Shell shell) {
        super(shell, path);

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
     * The canonical name of the bundle's target iOS SDK.
     */
    public String sdkCanonicalName() {
        return plist().stringValue(SDK_CANONICAL_NAME);
    }
}

package com.dhemery.victor.discovery;

import com.dhemery.os.Shell;
import com.dhemery.osx.ApplicationBundle;

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
     */
    public IosApplicationBundle(String path, Shell shell) {
        super(path, shell);
    }

    /**
     * The list of device types on which this application can run.
     */
    public List<String> deviceTypes() {
        Integer count = json().size(DEVICE_FAMILIES);
        List<String> deviceTypes = new ArrayList<String>(count);
        for(int i = 0 ; i < count ; i++) {
            String familyNumber = json().stringValue(DEVICE_FAMILIES, i);
            if(familyNumber.equals("1")) deviceTypes.add("iPhone");
            if(familyNumber.equals("2")) deviceTypes.add("iPad");
        }
        return deviceTypes;
    }

    /**
     * The canonical name of the bundle's target iOS SDK.
     */
    public String sdkCanonicalName() {
        return json().stringValue(SDK_CANONICAL_NAME);
    }
}

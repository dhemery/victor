package com.dhemery.victor.discovery;

import com.dhemery.os.Shell;
import com.dhemery.osx.FileSystemApplicationBundle;
import com.dhemery.victor.IosApplicationBundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an iOS application bundle on the file system.
 */
public class FileSystemIosApplicationBundle extends FileSystemApplicationBundle implements IosApplicationBundle {
    private static final String DEVICE_FAMILIES = "UIDeviceFamily";
    private static final String SDK_CANONICAL_NAME = "DTSDKName";

    /**
     * @param path the absolute file path to the application bundle
     * @param shell the shell to use to execute command line commands
     */
    public FileSystemIosApplicationBundle(String path, Shell shell) {
        super(path, shell);
    }

    @Override
    public List<String> deviceTypes() {
        Integer count = json().size(DEVICE_FAMILIES);
        List<String> deviceTypes = new ArrayList<>(count);
        for(int i = 0 ; i < count ; i++) {
            String familyNumber = json().stringValue(DEVICE_FAMILIES, i);
            if(familyNumber.equals("1")) deviceTypes.add("iPhone");
            if(familyNumber.equals("2")) deviceTypes.add("iPad");
        }
        return deviceTypes;
    }

    @Override
    public String targetSdkCanonicalName() {
        return json().stringValue(SDK_CANONICAL_NAME);
    }
}

package com.dhemery.osx;

import com.dhemery.configuration.ConfigurationException;
import com.dhemery.os.Shell;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import java.io.File;

/**
 * Retrieves information from an application bundle.
 * This "inspector" reads information from the bundle's {@code Info.plist} file,
 * and also examines the permissions of the bundle's executable file.
 */
public class ApplicationBundle {
    private static final String BUNDLE_VERSION = "CFBundleVersion";
    private static final String BUNDLE_IDENTIFIER = "CFBundleIdentifier";
    private static final String EXECUTABLE_NAME = "CFBundleExecutable";
    private final Supplier<PListInspector> plist = Suppliers.memoize(plistSupplier());
    private final String path;
    private final Shell shell;

    /**
     * Create an "inspector" that retrieves information from a specified application bundle.
     * @param path the absolute file path to the application bundle.
     */
    public ApplicationBundle(String path, Shell shell) {
        this.path = path;
        this.shell = shell;
        requireFile(path, "application bundle");
    }

    /**
     * The bundle's version.
     */
    public String bundleIdentifier() {
        return plist().stringValue(BUNDLE_IDENTIFIER);
    }

    /**
     * The bundle's version.
     */
    public String bundleVersion() {
        return plist().stringValue(BUNDLE_VERSION);
    }

    /**
     * The name of the bundle's executable file.
     * This method does not verify that the file exists or that its "executable" bit is set.
     */
    public String executableName() {
        return plist().stringValue(EXECUTABLE_NAME);
    }

    /**
     * Report whether the bundle's executable file is executable.
     * The file is executable if it exists and its "executable" bit is set.
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
        return plist.get();
    }

    private Supplier<PListInspector> plistSupplier() {
        return new Supplier<PListInspector>() {
            @Override
            public PListInspector get() {
                String plistPath = path + "/Info.plist";
                requireFile(plistPath, "Info.plist in application bundle");
                return new PListInspector(plistPath, shell);
            }
        };
    }

    private static void requireFile(String path, String description) {
        File file = new File(path);
        if(!file.exists()) {
            throw new ConfigurationException(String.format("Can not find %s at %s", description, path));
        }
    }
}

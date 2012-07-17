package com.dhemery.osx;

import com.dhemery.os.Shell;
import com.dhemery.configuration.ConfigurationException;

import java.io.File;

/**
 * Represents an application bundle.
 */
public class ApplicationBundle {
    private static final String BUNDLE_VERSION = "CFBundleVersion";
    private static final String BUNDLE_IDENTIFIER = "CFBundleIdentifier";
    private static final String EXECUTABLE_NAME = "CFBundleExecutable";
    protected final Shell shell;
    protected final String path;
    private PListInspector plist;

    /**
     * @param path the absolute file path to the application bundle.
     * @param shell the shell used to run commands to discover information from the application bundle.
     */
    public ApplicationBundle(Shell shell, String path) {
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
     * The name of the bundle's executable file.
     */
    public String executableName() {
        return plist().stringValue(EXECUTABLE_NAME);
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

    private void requireFile(String path, String description) {
        File file = new File(path);
        if(!file.exists()) {
            throw new ConfigurationException(String.format("Can not find %s at %s", description, path));
        }
    }
}

package com.dhemery.osx;

import com.dhemery.builder.Builder;
import com.dhemery.builder.Lazily;
import com.dhemery.builder.Lazy;
import com.dhemery.configuration.GsonJsonInspector;
import com.dhemery.configuration.JsonInspector;
import com.dhemery.os.Shell;

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
    private final String path;
    private final Shell shell;
    private final Lazy<JsonInspector> inspector = Lazily.from(inspectorBuilder());

    private Builder<JsonInspector> inspectorBuilder() {
        return new Builder<JsonInspector>() {
            @Override
            public JsonInspector build() {
                String json = new PlutilPlistReader(shell).read(path + "/Info.plist");
                return new GsonJsonInspector(json);
            }
        };
    }

    /**
     * Create an "inspector" that retrieves information from a specified application bundle.
     * @param path the absolute file path to the application bundle.
     */
    public ApplicationBundle(String path, Shell shell) {
        this.path = path;
        this.shell = shell;
    }

    /**
     * The bundle's version.
     */
    public String bundleIdentifier() {
        return json().stringValue(BUNDLE_IDENTIFIER);
    }

    /**
     * The bundle's version.
     */
    public String bundleVersion() {
        return json().stringValue(BUNDLE_VERSION);
    }

    /**
     * The name of the bundle's executable file.
     * This method does not verify that the file exists or that its "executable" bit is set.
     */
    public String executableName() {
        return json().stringValue(EXECUTABLE_NAME);
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
     * A {@link JsonInspector} with the contents of the bundle's Info.plist file.
     */
    public JsonInspector json() {
        return inspector.get();
    }
}

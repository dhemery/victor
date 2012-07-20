package com.dhemery.osx;

import com.dhemery.configuration.JsonInspector;
import com.dhemery.os.OSCommand;
import com.dhemery.os.OSCommandBuilder;
import com.dhemery.os.Shell;

/**
 * A JSON inspector for properties read from a plist file.
 */
public class PListInspector extends JsonInspector {
    /**
     * @param path the absolute path to the plist file to read.
     */
    public PListInspector(String path, Shell shell) {
        super(plistAsJson(path, shell));
    }

    private static String plistAsJson(String path, Shell shell) {
        OSCommand command = new OSCommandBuilder("Read PList", "plutil")
                                .withArguments("-convert", "json", "-o", "-", "--", path)
                                .build();
        return shell.run(command).output();
    }
}

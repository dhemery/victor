package com.dhemery.mac;

import com.dhemery.os.Shell;
import com.dhemery.os.ShellCommand;
import com.dhemery.os.OSCommand;
import com.dhemery.configuration.JsonInspector;

/**
 * A JSON inspector for properties read from a plist file.
 */
public class PListInspector extends JsonInspector {
    /**
     * @param path the absolute path to the plist file to read.
     * @param shell the shell to use to run the {@code plist} command.
     */
    public PListInspector(String path, Shell shell) {
        super(plistAsJson(path, shell));
    }

    private static String plistAsJson(String path, Shell shell) {
        OSCommand command = new ShellCommand("plutil")
                                .withArguments("-convert", "json", "-o", "-", "--", path)
                                .describedAs("Read PList");
        return shell.outputFrom(command);
    }
}

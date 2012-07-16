package com.dhemery.victor.discovery;

import com.dhemery.victor.OSCommand;
import com.dhemery.victor.os.Shell;
import com.dhemery.victor.os.ShellCommand;

/**
 * A JSON inspector for properties read from a plist file.
 */
public class PListInspector extends JsonInspector {
    /**
     * Create an inspector by reading a plist file as JSON.
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

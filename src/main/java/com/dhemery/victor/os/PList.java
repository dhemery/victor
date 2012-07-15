package com.dhemery.victor.os;

import com.dhemery.victor.OSCommand;

public class PList extends JsonInspector {
    public PList(Shell shell, String path) {
        super(plistAsJson(shell, path));
    }

    private static String plistAsJson(Shell shell, String path) {
        OSCommand command = new ShellCommand("plutil")
                                .withArguments("-convert", "json", "-o", "-", "--", path)
                                .describedAs("Read PList");
        return shell.outputFrom(command);
    }
}

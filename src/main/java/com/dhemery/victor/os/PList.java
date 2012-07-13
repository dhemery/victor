package com.dhemery.victor.os;

public class PList extends JsonInspector {
    public PList(Shell shell, String path) {
        super(plistAsJson(shell, path));
    }

    private static String plistAsJson(Shell shell, String path) {
        Command command = new ShellCommand("plutil").withArguments("-convert", "json", "-o", "-", "--", path);
        return shell.outputFrom(command);
    }
}

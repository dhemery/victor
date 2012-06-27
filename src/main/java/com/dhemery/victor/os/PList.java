package com.dhemery.victor.os;

import java.util.Arrays;
import java.util.List;

public class PList extends JsonInspector {

    public PList(String path) {
        super(plistAsJson(path));
    }

    private static String plistAsJson(String path) {
        List<String> arguments = Arrays.asList("-convert", "json", "-o", "-", "--", path);
        OSCommand command = new OSCommand("plutil", arguments);
        return command.output();
    }
}

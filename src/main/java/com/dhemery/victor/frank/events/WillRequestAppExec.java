package com.dhemery.victor.frank.events;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WillRequestAppExec {
    private final String name;
    private final List<Object> arguments;

    public WillRequestAppExec(String name, Object[] arguments) {
        this.name = name;
        this.arguments = Collections.unmodifiableList(Arrays.asList(arguments));
    }
    public String name() { return name; }
    public List<Object> arguments() { return arguments; }
}

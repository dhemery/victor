package com.dhemery.victor.frank.events;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AppExecReturned {
    private final String name;
    private final List<Object> arguments;
    private final String returnValue;

    public AppExecReturned(String name, Object[] arguments, String returnValue) {
        this.name = name;
        this.arguments = Collections.unmodifiableList(Arrays.asList(arguments));
        this.returnValue = returnValue;
    }
    public String name() { return name; }
    public List<Object> arguments() { return arguments; }
    public String returnValue() { return returnValue; }
}

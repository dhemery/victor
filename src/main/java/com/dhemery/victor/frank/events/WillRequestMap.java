package com.dhemery.victor.frank.events;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WillRequestMap {
    private final String engine;
    private final String query;
    private final String name;
    private final List<Object> arguments;

    public WillRequestMap(String engine, String query, String name, Object[] arguments) {
        this.engine = engine;
        this.query = query;
        this.name = name;
        this.arguments = Collections.unmodifiableList(Arrays.asList(arguments));
    }
    public String engine() { return engine; }
    public String query() { return query; }
    public String name() { return name; }
    public List<Object> arguments() { return arguments; }
}

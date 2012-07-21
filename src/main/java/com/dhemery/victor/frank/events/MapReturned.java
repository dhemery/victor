package com.dhemery.victor.frank.events;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MapReturned {
    private final String engine;
    private final String query;
    private final String name;
    private final List<Object> arguments;
    private final List<String> returnValues;

    public MapReturned(String engine, String query, String name, Object[] arguments, List<String> returnValues) {
        this.engine = engine;
        this.query = query;
        this.name = name;
        this.arguments = Collections.unmodifiableList(Arrays.asList(arguments));
        this.returnValues = Collections.unmodifiableList(returnValues);
    }
    public String engine() { return engine; }
    public String query() { return query; }
    public String name() { return name; }
    public List<Object> arguments() { return arguments; }
    public List<String> returnValues() { return returnValues; }
}

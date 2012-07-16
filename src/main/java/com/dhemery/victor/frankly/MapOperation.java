package com.dhemery.victor.frankly;

import java.io.Serializable;

public class MapOperation implements Serializable {
    private final String selector_engine;
    private final String query;
    private final Operation operation;

    public MapOperation(String engine, String query, Operation operation) {
        selector_engine = engine;
        this.query = query;
        this.operation = operation;
    }

    public String engine() { return selector_engine; }
    public String query() { return query; }
    public Operation operation() { return operation; }
}

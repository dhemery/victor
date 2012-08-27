package com.victor.fixtures;

import com.dhemery.core.Builder;
import com.dhemery.victor.frank.frankly.MapOperation;
import com.dhemery.victor.frank.frankly.Operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapOperationBuilder implements Builder<MapOperation> {
    private String engine;
    private String query;
    private String method;
    private final List<Object> arguments = new ArrayList<Object>();

    public Builder<MapOperation> engine(String engine) {
        this.engine = engine;
        return this;
    }

    public Builder<MapOperation> query(String query) {
        this.query = query;
        return this;
    }

    public Builder<MapOperation> method(String method) {
        this.method = method;
        return this;
    }

    public Builder<MapOperation> arguments(Object... arguments) {
        this.arguments.addAll(Arrays.asList(arguments));
        return this;
    }

    @Override
    public MapOperation build() {
        return new MapOperation(engine, query, new Operation(method, arguments.toArray()));
    }
}

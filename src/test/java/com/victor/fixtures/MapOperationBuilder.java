package com.victor.fixtures;

import com.dhemery.core.Builder;
import com.dhemery.victor.frank.frankly.MapOperation;
import com.dhemery.victor.frank.frankly.Operation;
import com.victor.frank.tests.FranklyFrankTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapOperationBuilder implements Builder<MapOperation> {
    private String engine = FranklyFrankTest.IGNORED_ENGINE;
    private String query = FranklyFrankTest.IGNORED_QUERY;
    private String method = FranklyFrankTest.IGNORED_METHOD_NAME;
    private List<Object> arguments = new ArrayList<Object>();

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

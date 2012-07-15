package com.dhemery.victor.frankly;

import com.dhemery.victor.io.Endpoint;
import com.dhemery.victor.io.Request;
import com.dhemery.victor.io.Response;
import com.google.gson.annotations.SerializedName;

public class MapRequest implements Request {
    @SerializedName("selector_engine")
    private final String engine;
    private final String query;
    private final Operation operation;

    public MapRequest(String engine, String query, Operation operation) {
        this.engine = engine;
        this.query = query;
        this.operation = operation;
    }

    public MessageResponse sendTo(Endpoint endpoint) {
        Response response = endpoint.put(this);
        return null;
    }

    public String engine() { return engine; }
    public String query() { return query; }
    @Override public String verb() { return "map"; }
}

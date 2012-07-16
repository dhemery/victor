package com.dhemery.victor.frankly;

import com.dhemery.victor.io.Endpoint;
import com.dhemery.victor.io.Json;
import com.dhemery.victor.io.Request;
import com.google.gson.annotations.SerializedName;

public class MapRequest implements Request {
    private final MapOperation mapOperation;
    @SerializedName("selector_engine")
    private final Json json;

    public MapRequest(String engine, String query, Operation operation, Json json) {
        mapOperation = new MapOperation(engine, query, operation);
        this.json = json;
    }

    public MessageResponse sendTo(Endpoint endpoint) {
        MessageResponse response = endpoint.put(path(), json.toJson(mapOperation()));
        return response;
    }

    public MapOperation mapOperation() { return mapOperation; }
    @Override public String path() { return "map"; }
}

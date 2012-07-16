package com.dhemery.victor.frankly;

import com.dhemery.victor.io.Endpoint;
import com.dhemery.victor.io.Json;
import com.dhemery.victor.io.Request;

public class AppExecRequest implements Request {
    private final Operation operation;
    private final Json json;

    public AppExecRequest(Operation operation, Json json) {
        this.operation = operation;
        this.json = json;
    }

    public MessageResponse sendTo(Endpoint endpoint) {
        MessageResponse response = endpoint.put(path(), json.toJson(operation()));
        return response;
    }

    public Operation operation() { return operation; }
    @Override public String path() { return "app_exec"; }
}

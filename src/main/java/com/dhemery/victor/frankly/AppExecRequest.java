package com.dhemery.victor.frankly;

import com.dhemery.victor.io.Endpoint;
import com.dhemery.victor.io.Request;
import com.dhemery.victor.io.Response;

public class AppExecRequest implements Request {
    private final Operation operation;

    public AppExecRequest(Operation operation) {
        this.operation = operation;
    }

    public MessageResponse sendTo(Endpoint endpoint) {
        Response response = endpoint.put(this);
        return null;
    }

    public Operation operation() { return operation; }
    @Override public String verb() { return "app_exec"; }
}
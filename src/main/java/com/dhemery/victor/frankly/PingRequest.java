package com.dhemery.victor.frankly;

import com.dhemery.victor.io.Endpoint;
import com.dhemery.victor.io.Request;

public class PingRequest implements Request {
    public PingResponse sendTo(Endpoint endpoint) {
        try {
            endpoint.get(this);
            return new PingResponse(true);
        } catch (Throwable ignored) {
            return new PingResponse(false);
        }
    }

    @Override public String verb() { return "ping"; }
}

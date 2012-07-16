package com.dhemery.victor.frankly;

import com.dhemery.victor.io.Endpoint;
import com.dhemery.victor.io.Request;

public class PingRequest implements Request {
    public PingResponse sendTo(Endpoint endpoint) {
        try {
            return endpoint.get(path());
        } catch (Throwable ignored) {
            return new PingResponse(false);
        }
    }

    @Override public String path() { return "ping"; }
}

package com.dhemery.victor.frankly;

import com.dhemery.victor.io.Response;

public class PingResponse implements Response {
    private final boolean responded;

    public PingResponse(boolean responded) {
        this.responded = responded;
    }

    public boolean responded() { return responded; }
}

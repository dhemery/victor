package com.dhemery.victor.frankly;

public class PingResponse {
    private final boolean responded;

    public PingResponse(boolean responded) {
        this.responded = responded;
    }

    public boolean responded() { return responded; }
}

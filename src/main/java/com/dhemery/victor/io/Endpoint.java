package com.dhemery.victor.io;

public interface Endpoint {
    String protocol();
    String host();
    int port();
    Response get(Request request);
    Response put(Request request);
}

package com.dhemery.victor.io;

public interface Endpoint {
    Response get(Request request);
    Response put(Request request);
}

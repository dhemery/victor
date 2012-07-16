package com.dhemery.victor.io;

public interface Endpoint {
    String protocol();
    String host();
    int port();
    <R extends Response> R get(String path);
    <R extends Response> R put(String path, String body);
}

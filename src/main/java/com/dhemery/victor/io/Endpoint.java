package com.dhemery.victor.io;

public interface Endpoint {
    String protocol();
    String host();
    int port();
    String get(String path);
    String put(String path, String body);
}

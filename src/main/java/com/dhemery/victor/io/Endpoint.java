package com.dhemery.victor.io;

public interface Endpoint {
    Protocol protocol();
    String host();
    int port();
    String get(String path);
    String put(String path, String message);
}

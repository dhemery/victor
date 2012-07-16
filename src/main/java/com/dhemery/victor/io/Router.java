package com.dhemery.victor.io;

public interface Router {
    Connection connectionFor(String host, int port, String path);
    String protocol();
}

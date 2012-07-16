package com.dhemery.victor.io;

public interface Connection {
    void connect();
    void write(String body);
    String responseStatus();
    String responseBody();
    void disconnect();
}

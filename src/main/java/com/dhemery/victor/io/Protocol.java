package com.dhemery.victor.io;

public interface Protocol {
    String name();
    String get(Endpoint endpoint, String path);
    String put(Endpoint endpoint, String path, String message);
}

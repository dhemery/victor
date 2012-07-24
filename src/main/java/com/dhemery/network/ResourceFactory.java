package com.dhemery.network;

public interface ResourceFactory {
    Resource resource(String protocol, String host, int port, String path);
}

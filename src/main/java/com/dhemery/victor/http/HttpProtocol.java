package com.dhemery.victor.http;

import com.dhemery.victor.io.Connection;
import com.dhemery.victor.io.Endpoint;
import com.dhemery.victor.io.Protocol;

public class HttpProtocol implements Protocol {
    private static final String PROTOCOL = "http";

    @Override
    public String name() {
        return PROTOCOL;
    }

    //TODO: Implement
    @Override
    public String get(Endpoint endpoint, String path) {
        Connection connection = connection(endpoint, path);
        return null;
    }

    //TODO: Implement
    @Override
    public String put(Endpoint endpoint, String path, String message) {
        Connection connection = connection(endpoint, path);
        return null;
    }

    private HttpConnection connection(Endpoint endpoint, String path) {
        return new HttpConnection(name(), endpoint.host(),  endpoint.port(), path);
    }
}

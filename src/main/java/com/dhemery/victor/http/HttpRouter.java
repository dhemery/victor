package com.dhemery.victor.http;

import com.dhemery.victor.io.Connection;
import com.dhemery.victor.io.Router;

public class HttpRouter implements Router {
    public HttpRouter() {
    }

    @Override
    public Connection connectionFor(String host, int port, String path) {
        return new HttpConnection(host, port, path);
    }

    @Override
    public String protocol() {
        return HttpConnection.PROTOCOL;
    }
}

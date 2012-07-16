package com.dhemery.victor.http;

import com.dhemery.victor.io.Endpoint;
import com.dhemery.victor.io.Response;

import java.net.MalformedURLException;
import java.net.URL;

public class HttpEndpoint implements Endpoint {
    public static final String PROTOCOL = "http";
    private final String host;
    private final int port;

    public HttpEndpoint(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public <R extends Response> R get(String path) {
        HttpConnection connection = connectionFor(path);
        HttpResponse response = connection.response();
        return null;
    }

    @Override
    public <R extends Response> R put(String path, String body) {
        HttpConnection connection = connectionFor(path);
        connection.write(body);
        HttpResponse response = connection.response();
        return null;
    }

    @Override public String host() { return host; }
    @Override public int port() { return port; }
    @Override public String protocol() { return PROTOCOL; }

    private HttpConnection connectionFor(String path) {
        return new HttpConnection(urlFor(path));
    }

    private URL urlFor(String path) {
        try {
            return new URL(protocol(), host(), port(), path);
        } catch (MalformedURLException cause) {
            throw new HttpException("Cannot create URL", cause);
        }
    }
}

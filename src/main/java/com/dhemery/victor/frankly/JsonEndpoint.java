package com.dhemery.victor.frankly;

import com.dhemery.victor.http.HttpConnection;
import com.dhemery.victor.http.HttpException;
import com.dhemery.victor.io.*;

import java.net.MalformedURLException;
import java.net.URL;

public class JsonEndpoint implements Endpoint {
    private final String protocol;
    private final String host;
    private final int port;
    private final Json json;

    public JsonEndpoint(String protocol, String host, int port, Json json) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.json = json;
    }

    @Override public String host() { return host; }
    @Override public int port() { return port; }
    @Override public String protocol() { return protocol; }

    @Override
    public Response get(Request request) {
        Connection connection = connectionFor(request);
        Response response = connection.response();
        return response;
    }

    @Override
    public Response put(Request request) {
        String body = json.toJson(request);
        Connection connection = connectionFor(request);
        connection.write(body);
        Response response = connection.response();
        return response;
    }

    private Connection connectionFor(Request request) {
        return new HttpConnection(urlFor(request.verb()));
    }

    private URL urlFor(String path) {
        try {
            return new URL(protocol, host, port, path);
        } catch (MalformedURLException cause) {
            throw new HttpException("Cannot create URL", cause);
        }
    }
}

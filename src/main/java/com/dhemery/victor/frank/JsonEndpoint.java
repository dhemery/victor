package com.dhemery.victor.frank;

import com.dhemery.victor.http.HttpConnection;
import com.dhemery.victor.io.Connection;
import com.dhemery.victor.io.Endpoint;
import com.dhemery.victor.io.Request;
import com.dhemery.victor.io.Response;
import com.google.gson.Gson;

public class JsonEndpoint implements Endpoint {
    private final String url;
    private final Gson gson;

    public JsonEndpoint(String url) {
        this.url = url;
        gson = new Gson();
    }

    private Connection connectionFor(Request request) {
        return new HttpConnection(String.format("%s/%s", url, request.verb()));
    }

    @Override
    public Response get(Request request) {
        Connection connection = connectionFor(request);
        Response response = connection.response();
        return response;
    }

    @Override
    public Response put(Request request) {
        String body = gson.toJson(request);
        Connection connection = connectionFor(request);
        connection.write(body);
        Response response = connection.response();
        return response;
    }
}

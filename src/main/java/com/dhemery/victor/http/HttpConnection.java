package com.dhemery.victor.http;

import com.dhemery.victor.io.Connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpConnection implements Connection<HttpResponse> {
    public static final int READ_TIMEOUT = 30000;
    private final HttpURLConnection connection;

    public HttpConnection(URL url) {
        connection = connectTo(url);
    }

    @Override
    public void write(String body) {
        try {
            connection.getOutputStream().write(body.getBytes());
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot write request body %s to %s", body, connection), cause);
        }
    }

    @Override
    public HttpResponse response() {
        HttpResponse response = responseFrom(connection);
        disconnectFrom(connection);
        return response;
    }

    private HttpURLConnection connectTo(URL url) {
        HttpURLConnection connection = openHttpConnection(url);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setDoOutput(true);
        connectTo(connection);
        return connection;
    }

    private HttpURLConnection openHttpConnection(URL url) {
        try {
            return (HttpURLConnection) url.openConnection();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot open connection to %s", url), cause);
        }
    }

    private void connectTo(URLConnection connection) {
        try {
            connection.connect();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot connect to %s", connection.getURL()), cause);
        }
    }

    private HttpResponse responseFrom(HttpURLConnection connection) {
        return new HttpResponse(responseMessageFrom(connection), responseBodyFrom(connection));
    }

    private String responseMessageFrom(HttpURLConnection connection) {
        try {
            return connection.getResponseMessage();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot read response from %s", connection.getURL()), cause);
        }
    }

    private String responseBodyFrom(HttpURLConnection connection) {
        return new HttpResponseBodyReader(connection).read();
    }

    private void disconnectFrom(HttpURLConnection connection) {
        connection.disconnect();
    }
}
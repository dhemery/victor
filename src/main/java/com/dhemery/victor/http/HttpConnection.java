package com.dhemery.victor.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpConnection {
    public static final int READ_TIMEOUT = 30000;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final URL url;

    public HttpConnection(URL url) {
        this.url = url;
    }

    private HttpURLConnection connection() {
        HttpURLConnection connection = openHttpConnection();
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setDoOutput(true);
        connectTo(connection);
        return connection;
    }

    private void connectTo(URLConnection connection) {
        try {
            connection.connect();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot connect to %s", connection.getURL()), cause);
        }
    }

    private void disconnectFrom(HttpURLConnection connection) {
        connection.disconnect();
    }

    private HttpURLConnection openHttpConnection() {
        try {
            return (HttpURLConnection) url.openConnection();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot open connection to %s", url), cause);
        }
    }

    private HttpResponse responseFrom(HttpURLConnection connection) {
        HttpResponse response = new HttpResponse(responseMessageFrom(connection), responseBodyFrom(connection));
        log.trace("Response: {}", response);
        return response;
    }

    private String responseBodyFrom(HttpURLConnection connection) {
        return new HttpResponseBodyReader(connection).read();
    }

    private String responseMessageFrom(HttpURLConnection connection) {
        try {
            return connection.getResponseMessage();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot read response from %s", connection.getURL()), cause);
        }
    }

    public HttpResponse send(HttpRequestBody body) {
        HttpURLConnection connection = connection();
        body.writeTo(connection);
        HttpResponse response = responseFrom(connection);
        disconnectFrom(connection);
        return response;
    }
}

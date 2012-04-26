package com.dhemery.victor.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class HttpResponseBodyReader {
    private final HttpURLConnection connection;

    public HttpResponseBodyReader(HttpURLConnection connection) {
        this.connection = connection;
    }

    private void close(BufferedReader reader) {
        try {
            reader.close();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot close reader for %s", connection.getURL()), cause);
        }
    }

    private InputStream inputStream() {
        try {
            return connection.getInputStream();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot get input stream for %s", connection.getURL()), cause);
        }
    }

    private String nextLineFrom(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot read next line from %s", connection.getURL()), cause);
        }
    }

    public String read() {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = reader();
        String line;
        while ((line = nextLineFrom(reader)) != null) {
            builder.append(line);
        }
        close(reader);
        return builder.toString();
    }

    private BufferedReader reader() {
        return new BufferedReader(new InputStreamReader(inputStream()));
    }
}

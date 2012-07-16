package com.dhemery.victor.http;

import com.dhemery.victor.HttpException;
import com.dhemery.victor.io.Connection;
import com.dhemery.victor.io.StreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpConnection implements Connection {
    public static final String PROTOCOL = "http";
    private static final int READ_TIMEOUT = 30000;
    private final HttpURLConnection connection;
    private final StreamReader streamReader = new StreamReader();

    public HttpConnection(String host, int port, String path) {
        connection = httpUrlConnectionFor(urlFor(host, port, path));
    }

    @Override
    public void connect() {
        try {
            connection.connect();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot connect to %s", connection.getURL()), cause);
        }
    }

    @Override
    public void write(String body) {
        OutputStream outputStream = outputStream();
        try {
            outputStream.write(body.getBytes());
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot write request body %s to %s", body, connection), cause);
        }
    }

    @Override
    public String responseStatus() {
        try {
            return connection.getResponseMessage();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot read response status from %s", connection.getURL()), cause);
        }
    }

    @Override
    public String responseBody() {
        return streamReader.read(inputStream(), connection.getURL().toString());
    }

    @Override
    public void disconnect() {
        connection.disconnect();
    }

    private URL urlFor(String host, int port, String path) {
        try {
            return new URL(PROTOCOL, host, port, path);
        } catch (MalformedURLException cause) {
            throw new HttpException("Cannot create URL", cause);
        }
    }

    private InputStream inputStream() {
        try {
            return connection.getInputStream();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot get input stream for %s", connection.getURL()), cause);
        }
    }

    private OutputStream outputStream() {
        try {
            return connection.getOutputStream();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot get output stream for %s", connection), cause);
        }
    }

    private HttpURLConnection httpUrlConnectionFor(URL url) {
        HttpURLConnection connection = openHttpURLConnection(url);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setDoOutput(true);
        return connection;
    }

    private HttpURLConnection openHttpURLConnection(URL url) {
        try {
            return (HttpURLConnection) url.openConnection();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot open connection to %s", url), cause);
        }
    }

}
package com.dhemery.network;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * A resource accessible through a URL.
 */
public class URLResource implements Resource {
    private static final boolean FOR_READING = false;
    private static final boolean FOR_WRITING = true;
    private static final int READ_TIMEOUT = 30000;
    private final URLConnection connection;

    /**
     * Create a resource with the specified URL.
     */
    public URLResource(URL url) {
        connection = connection(url);
    }

    @Override
    public String get() {
        connect(FOR_READING);
        return read();
    }

    @Override
    public String put(String message) {
        connect(FOR_WRITING);
        write(message);
        return read();
    }

    private static URLConnection connection(URL url) {
        try {
            return url.openConnection();
        } catch (IOException cause) {
            throw new NetworkKitException(String.format("Cannot create connection to %s", url), cause);
        }
    }

    private void connect(boolean forWriting) {
        try {
            connection.setDoInput(true);
            connection.setDoOutput(forWriting);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.connect();
        } catch (IOException cause) {
            throw new NetworkKitException("Cannot connect to " + connection, cause);
        }
    }

    private void write(String message) {
        OutputStreamWriter writer = writer();
        try {
            writer.write(message);
        } catch (IOException cause) {
            throw new NetworkKitException("Cannot write to " + connection, cause);
        }
        close(writer);
    }

    private OutputStreamWriter writer() {
        try {
            return new OutputStreamWriter(connection.getOutputStream());
        } catch (IOException cause) {
            throw new NetworkKitException("Cannot get output stream for " + connection, cause);
        }
    }

    private String read() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while((line = readLine(reader)) != null) {
            builder.append(line);
        }
        close(reader);
        return builder.toString();
    }

    private InputStream inputStream() {
        try {
            return connection.getInputStream();
        } catch (IOException cause) {
            throw new NetworkKitException("Cannot get input stream for" + connection, cause);
        }
    }

    private String readLine(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException cause) {
            throw new NetworkKitException("Cannot read line from " + connection, cause);
        }
    }

    private void close(Writer writer) {
        try {
            writer.close();
        } catch (IOException cause) {
            throw new NetworkKitException("Cannot close writer for " + connection, cause);
        }
    }

    private void close(BufferedReader reader) {
        try {
            reader.close();
        } catch (IOException cause) {
            throw new NetworkKitException("Cannot close reader for " + connection, cause);
        }
    }
}

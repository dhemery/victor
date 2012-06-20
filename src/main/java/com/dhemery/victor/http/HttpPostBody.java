package com.dhemery.victor.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * The body of an HTTP POST request.
 * Writes itself to the connection as a JSON string.
 *
 * @author Dale Emery
 */
public class HttpPostBody implements HttpRequestBody {
    private final transient Logger log = LoggerFactory.getLogger(getClass());
    /**
     * Write this request body to the connection as a JSON string.
     * This causes the request to be sent via HTTP POST.
     */
    @Override
    public void writeTo(HttpURLConnection connection) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(this);
        log.trace("Writing body as Json: {}", json);
        write(connection, json);
    }

    private void write(HttpURLConnection connection, String json) {
        try {
            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter out = new OutputStreamWriter(outputStream);
            out.write(json);
            out.close();
        } catch (IOException e) {
            throw new HttpException(String.format("Cannot write to %s", connection.getURL()), e);
        }
    }
}

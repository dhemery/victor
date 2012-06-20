package com.dhemery.victor.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;

/**
 * The "body" of an HTTP GET request.
 */
public class HttpGetBody implements HttpRequestBody {
    private final transient Logger log = LoggerFactory.getLogger(getClass());

    /**
     * This method refrains from writing to the connection,
     * which causes the request to be sent via HTTP GET.
     *
     * @param connection ignored.
     */
    @Override
    public void writeTo(HttpURLConnection connection) {
        log.trace("Sending empty body to {} via HTTP GET", connection.getURL());
    }

    @Override
    public String toString() {
        return "";
    }
}
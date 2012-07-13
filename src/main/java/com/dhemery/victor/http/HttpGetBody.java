package com.dhemery.victor.http;

import java.net.HttpURLConnection;

/**
 * The "body" of an HTTP GET request.
 */
public class HttpGetBody implements HttpRequestBody {
    /**
     * This method refrains from writing to the connection,
     * which causes the request to be sent via HTTP GET.
     *
     * @param connection ignored.
     */
    @Override
    public void writeTo(HttpURLConnection connection) {}

    @Override
    public String toString() {
        return "";
    }
}
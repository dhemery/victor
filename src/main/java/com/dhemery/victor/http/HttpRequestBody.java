package com.dhemery.victor.http;

import java.net.HttpURLConnection;

/**
 * The body of an HTTP request.
 */
public interface HttpRequestBody {
    /**
     * Write the body of this request through the connection.
     */
    void writeTo(HttpURLConnection connection);
}

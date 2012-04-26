package com.dhemery.victor.http;

/**
 * An HTTP server response from a request that returns JSON strings.
 *
 * @author Dale Emery
 */
public class HttpResponse {
    private final String status;
    private final String body;

    public HttpResponse(String status, String body) {
        this.status = status;
        this.body = body;
    }

    /**
     * @return body of the response.
     */
    public String body() {
        return body;
    }

    /**
     * @return the HTTP response status of the response.
     */
    public String status() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", status(), body());
    }
}

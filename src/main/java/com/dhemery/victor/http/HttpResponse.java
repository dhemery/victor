package com.dhemery.victor.http;

import com.dhemery.victor.io.Response;

public class HttpResponse implements Response {
    private final String status;
    private final String body;

    public HttpResponse(String status, String body) {
        this.status = status;
        this.body = body;
    }

    @Override public String body() { return body; }
    @Override public String status() { return status; }
}

package com.dhemery.victor.http;

import com.dhemery.victor.io.Response;

public class HttpResponse implements Response {
    private final String status;
    private final String body;

    public HttpResponse(String status, String body) {
        this.status = status;
        this.body = body;
    }

    public String body() { return body; }
    public String status() { return status; }
}

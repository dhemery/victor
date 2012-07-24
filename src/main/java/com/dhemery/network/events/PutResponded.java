package com.dhemery.network.events;

public class PutResponded {
    private final String path;
    private final String message;
    private final String response;

    public PutResponded(String path, String message, String response) {
        this.path = path;
        this.message = message;
        this.response = response;
    }
    public String path() { return path; }
    public String message() { return message; }
    public String response() { return response; }
}

package com.dhemery.network.events;

public class GetResponded {
    private final String path;
    private final String response;

    public GetResponded(String path, String response) {
        this.path = path;
        this.response = response;
    }
    public String path() { return path; }
    public String response() { return response; }
}

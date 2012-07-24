package com.dhemery.network.events;

public class GetThrew {
    private final String path;
    private final Throwable exception;

    public GetThrew(String path, Throwable exception) {
        this.path = path;
        this.exception = exception;
    }
    public String path() { return path; }
    public Throwable exception() { return exception; }
}

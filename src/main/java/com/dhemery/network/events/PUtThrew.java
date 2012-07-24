package com.dhemery.network.events;

public class PutThrew {
    private final String path;
    private final String message;
    private final Throwable exception;

    public PutThrew(String path, String message, Throwable exception) {
        this.path = path;
        this.message = message;
        this.exception = exception;
    }
    public String path() { return path; }
    public String message() { return message; }
    public Throwable exception() { return exception; }
}

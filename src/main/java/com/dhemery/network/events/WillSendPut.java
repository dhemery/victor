package com.dhemery.network.events;

public class WillSendPut {
    private final String path;
    private final String message;

    public WillSendPut(String path, String message) {
        this.path = path;
        this.message = message;
    }
    public String path() { return path; }
    public String message() { return message; }
}

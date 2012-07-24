package com.dhemery.network.events;

public class WillSendGet {
    private final String path;

    public WillSendGet(String path) {
        this.path = path;
    }
    public String path() { return path ;}
}

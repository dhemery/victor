package com.dhemery.network.events;

import com.dhemery.network.Resource;

public class PutThrew {
    private final Resource resource;
    private final String message;
    private final Throwable exception;

    public PutThrew(Resource resource, String message, Throwable exception) {
        this.resource = resource;
        this.message = message;
        this.exception = exception;
    }
    public Resource resource() { return resource; }
    public String message() { return message; }
    public Throwable exception() { return exception; }
}

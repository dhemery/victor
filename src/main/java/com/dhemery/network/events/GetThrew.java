package com.dhemery.network.events;

import com.dhemery.network.Resource;

public class GetThrew {
    private final Resource resource;
    private final Throwable exception;

    public GetThrew(Resource resource, Throwable exception) {
        this.resource = resource;
        this.exception = exception;
    }
    public Resource resource() { return resource; }
    public Throwable exception() { return exception; }
}

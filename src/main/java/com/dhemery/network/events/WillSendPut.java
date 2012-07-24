package com.dhemery.network.events;

import com.dhemery.network.Resource;

public class WillSendPut {
    private final Resource resource;
    private final String message;

    public WillSendPut(Resource resource, String message) {
        this.resource = resource;
        this.message = message;
    }
    public Resource resource() { return resource; }
    public String message() { return message; }
}

package com.dhemery.network.events;

import com.dhemery.network.Resource;

public class WillSendGet {
    private final Resource resource;

    public WillSendGet(Resource resource) {
        this.resource = resource;
    }
    public Resource resource() { return resource; }
}

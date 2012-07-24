package com.dhemery.network.events;

import com.dhemery.network.Resource;

public class GetResponded {
    private final Resource resource;
    private final String response;

    public GetResponded(Resource resource, String response) {
        this.resource = resource;
        this.response = response;
    }
    public Resource resource() { return resource; }
    public String response() { return response; }
}

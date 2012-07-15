package com.dhemery.victor.frankly;

import com.dhemery.victor.io.Endpoint;
import com.dhemery.victor.io.Request;

public class OrientationRequest implements Request {
    public OrientationResponse sendTo(Endpoint endpoint) {
        OrientationResponse response = new OrientationResponse(endpoint.get(this));
        return response;
    }

    @Override public String verb() { return "orientation"; }
}

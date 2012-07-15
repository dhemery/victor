package com.dhemery.victor.frankly;

import com.dhemery.victor.io.Response;

public class OrientationResponse {
    private final String orientation;

    public OrientationResponse(Response response) {
        orientation = response.body();
    }

    public String orientation() {
        return null;
    }
}

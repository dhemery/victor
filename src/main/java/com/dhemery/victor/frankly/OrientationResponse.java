package com.dhemery.victor.frankly;

import com.dhemery.victor.io.Response;

public class OrientationResponse implements Response {
    private final String orientation;

    public OrientationResponse(String orientation) {
        this.orientation = orientation;
    }

    public String orientation() {
        return orientation;
    }
}

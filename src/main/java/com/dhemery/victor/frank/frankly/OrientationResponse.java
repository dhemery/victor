package com.dhemery.victor.frank.frankly;

/**
 * A response to an orientation() request.
 *
 * @author Dale Emery
 */
public class OrientationResponse {
    private final String orientation;

    public OrientationResponse(String orientation) {
        this.orientation = orientation;
    }

    public String orientation() {
        return orientation;
    }

    @Override
    public String toString() {
        return orientation;
    }
}

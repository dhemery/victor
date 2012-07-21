package com.dhemery.victor.frank.events;

public class OrientationReturned {
    private final String orientation;

    public OrientationReturned(String orientation) {
        this.orientation = orientation;
    }
    public String orientation() { return orientation; }
}

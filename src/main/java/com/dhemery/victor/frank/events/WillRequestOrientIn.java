package com.dhemery.victor.frank.events;

public class WillRequestOrientIn {
    private final String orientation;

    public WillRequestOrientIn(String orientation) {
        this.orientation = orientation;
    }

    public String orientation() { return orientation; }
}

package com.dhemery.victor.frank.events;

public class AccessibilityCheckReturned {
    private final boolean enabled;

    public AccessibilityCheckReturned(boolean enabled) {
        this.enabled = enabled;
    }
    public boolean enabled() { return enabled; }
}

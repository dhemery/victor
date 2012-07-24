package com.dhemery.victor.frank.frankly;

import com.google.gson.annotations.SerializedName;

public class AccessibilityCheckResponse {
    @SerializedName("accessibility_enabled")
    private final boolean enabled;

    public AccessibilityCheckResponse(boolean enabled) {
        this.enabled = enabled;
    }
    public boolean enabled() { return enabled; }
}

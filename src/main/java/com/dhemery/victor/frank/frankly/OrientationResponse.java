package com.dhemery.victor.frank.frankly;

import com.google.gson.annotations.SerializedName;

public class OrientationResponse {
    @SerializedName("orientation") private String notUsed;
    @SerializedName("detailed_orientation") private final String detailedOrientation;

    public OrientationResponse(String orientation) {
        detailedOrientation = orientation;
    }

    public String orientation() { return detailedOrientation; }
}

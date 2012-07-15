package com.dhemery.victor.frankly;

import com.dhemery.victor.io.Endpoint;
import com.dhemery.victor.io.Request;
import com.google.gson.annotations.SerializedName;

/**
 * Carries the text to type into the device's keyboard.
 */
public class TypeIntoKeyboardRequestBody implements Request {
    @SerializedName("text_to_type")
    private final String text;

    public TypeIntoKeyboardRequestBody(String text) {
        this.text = text;
    }

    public void sendTo(Endpoint endpoint) {
        endpoint.put(this);
    }

    public String text() { return text; }
    @Override public String verb() { return "type_into_keyboard"; }
}

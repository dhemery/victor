package com.dhemery.victor.frankly;

import java.io.Serializable;

/**
 * Carries the text to type into the device's keyboard.
 */
public class TextToType implements Serializable {
    private final String text_to_type;

    public TextToType(String text) {
        text_to_type = text;
    }

    public String text() { return text_to_type; }
}

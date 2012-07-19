package com.dhemery.victor.frankly;

import com.dhemery.victor.frank.Frank;

/**
 * Represents the text to type into the device's keyboard.
 * {@code TextToType} is a serializable representation of the argument to
 * {@link Frank#typeIntoKeyboard(String)}.
 * @see PublishingFrank
 */
public class TextToType {
    private final String text_to_type;

    /**
     * Create a representation of the text to type into the device's keyboard.
     */
    public TextToType(String text) {
        text_to_type = text;
    }

    /**
     * The text to type into the keyboard.
     */
    public String text() { return text_to_type; }
}

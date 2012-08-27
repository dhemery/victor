package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.frank.Frank;
import com.google.gson.annotations.SerializedName;

/**
 * Represents the text to type into the device's keyboard.
 * {@code TextToType} is a serializable representation of the argument to
 * {@link Frank#typeIntoKeyboard(String)}.
 * @see com.dhemery.victor.frank.FranklyFrank
 */
public class TextToType {
    @SerializedName("text_to_type")
    public final String text;

    /**
     * Create a representation of the text to type into the device's keyboard.
     */
    public TextToType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextToType that = (TextToType) o;

        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }
}

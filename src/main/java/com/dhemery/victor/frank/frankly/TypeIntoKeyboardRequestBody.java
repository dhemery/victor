package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.http.HttpPostBody;
import com.google.gson.annotations.SerializedName;

/**
 * Carries the text to type into the device's keyboard.
 */
public class TypeIntoKeyboardRequestBody extends HttpPostBody {
    @SerializedName("text_to_type")
    private final String textToType;

    public TypeIntoKeyboardRequestBody(String textToType) {
        this.textToType = textToType;
    }


    @Override
    public String toString() {
        return textToType;
    }
}

package com.dhemery.victor.frankly;

import com.dhemery.victor.io.Endpoint;
import com.dhemery.victor.io.Json;
import com.dhemery.victor.io.Request;

/**
 * Carries the text to type into the device's keyboard.
 */
public class TypeIntoKeyboardRequest implements Request {
    private final TextToType textToType;
    private final Json json;

    public TypeIntoKeyboardRequest(String text, Json json) {
        this.json = json;
        textToType = new TextToType(text);
    }

    public void sendTo(Endpoint endpoint) {
        TypeIntoKeyboardResponse response = endpoint.put(path(), json.toJson(textToType));
    }

    @Override public String path() { return "type_into_keyboard"; }
    public TextToType textToType() { return textToType; }
}

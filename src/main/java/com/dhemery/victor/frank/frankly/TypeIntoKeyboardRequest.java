package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.http.HttpRequest;

/**
 * A request to type keys into the device's keyboard.
 */
public class TypeIntoKeyboardRequest extends HttpRequest {
    public TypeIntoKeyboardRequest(String text) {
        super("type_into_keyboard", new TypeIntoKeyboardRequestBody(text));
    }
}

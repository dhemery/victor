package com.dhemery.victor.frankly;

import com.dhemery.network.Codec;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Serializes Frankly payloads and deserializes Frankly responses.
 * @see FranklyFrank
 */
public class FranklyJsonCodec implements Codec {
    private final Gson gson = new GsonBuilder()
                .registerTypeAdapter(MessageResponse.class, new MessageResponseParser())
                .disableHtmlEscaping()
                .create();

    @Override
    public <T> T decode(String representation, Class<T> resultType) {
        return gson.fromJson(representation, resultType);
    }

    @Override
    public String encode(Object object) {
        return gson.toJson(object);
    }
}

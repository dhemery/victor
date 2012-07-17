package com.dhemery.victor.frankly;

import com.dhemery.victor.io.Codec;
import com.google.gson.Gson;

public class FranklyJsonCodec implements Codec {
    private final Gson gson = new Gson();

    @Override
    public <T> T decode(String put, Class<T> resultType) {
        return null;
    }

    @Override
    public String encode(Object object) {
        return gson.toJson(object);
    }
}

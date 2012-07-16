package com.dhemery.victor.frankly;

import com.dhemery.victor.io.JsonEncoder;
import com.google.gson.Gson;

public class FranklyJsonEncoder implements JsonEncoder {
    private final Gson gson = new Gson();

    @Override
    public <T> T fromJson(String put, Class<T> resultType) {
        return null;
    }

    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }
}

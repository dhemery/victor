package com.dhemery.victor.frankly;

import com.dhemery.victor.io.Json;
import com.google.gson.Gson;

import java.io.Serializable;

public class FranklyEncoder implements Json {
    private final Gson gson = new Gson();

    @Override
    public String toJson(Serializable object) {
        return gson.toJson(object);
    }
}

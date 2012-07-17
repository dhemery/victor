package com.dhemery.victor.frankly;

import com.dhemery.victor.frank.Frank;
import com.dhemery.victor.io.*;

import java.util.List;

/**
 * A Frank agent that sends and receives messages using the "Frankly" protocol.
 */
public class FranklyFrank implements Frank {
    private static final String ORIENTATION_REQUEST = "orientation";
    private static final String PING_REQUEST = "ping";
    private static final String APP_EXEC = "app_exec";
    private static final String MAP = "map";
    private static final String TYPE_INTO_KEYBOARD = "type_into_keyboard";
    private final Endpoint endpoint;
    private final Codec codec;

    public FranklyFrank(Endpoint endpoint, Codec codec) {
        this.endpoint = endpoint;
        this.codec = codec;
    }

    @Override
    public String appExec(String name, Object...arguments) {
        Operation operation = new Operation(name, arguments);
        MessageResponse response = put(APP_EXEC, operation);
        return response.results().get(0);
    }

    @Override
    public List<String> map(String engine, String query, String name, Object...arguments) {
        Operation operation = new Operation(name, arguments);
        MapOperation mapOperation = new MapOperation(engine, query, operation);
        MessageResponse response = put(MAP, mapOperation);
        return response.results();
    }

    @Override
    public String orientation() {
        String response = get(ORIENTATION_REQUEST);
        return response;
    }

    @Override
    public boolean ping() {
        try {
            get(PING_REQUEST);
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    @Override
    public void typeIntoKeyboard(String text) {
        TextToType textToType = new TextToType(text);
        put(TYPE_INTO_KEYBOARD, textToType);
    }

    @Override public Endpoint endpoint() { return endpoint; }

    private String get(String path) {
        return endpoint.get(path);
    }

    private MessageResponse put(String path, Object payload) {
        String message = codec.encode(payload);
        String response = endpoint.put(path, message);
        return codec.decode(response, MessageResponse.class);
    }
}

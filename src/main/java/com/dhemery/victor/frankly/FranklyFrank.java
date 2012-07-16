package com.dhemery.victor.frankly;

import com.dhemery.victor.frank.Frank;
import com.dhemery.victor.io.Endpoint;
import com.dhemery.victor.io.Json;

import java.util.List;

/**
 * A Frank agent that sends and receives messages using the "Frankly" protocol.
 */
public class FranklyFrank implements Frank {
    private final Endpoint endpoint;
    private final Json json;

    public FranklyFrank(Endpoint endpoint, Json json) {
        this.endpoint = endpoint;
        this.json = json;
    }

    @Override public Endpoint endpoint() { return endpoint; }

    @Override
    public String appExec(String name, Object...arguments) {
        AppExecRequest request = new AppExecRequest(new Operation(name, arguments), json);
        MessageResponse response = request.sendTo(endpoint);
        return response.results().get(0);
    }

    @Override
    public List<String> map(String engine, String query, String name, Object...arguments) {
        MapRequest request = new MapRequest(engine, query, new Operation(name, arguments), json);
        MessageResponse response = request.sendTo(endpoint);
        return response.results();
    }

    @Override
    public String orientation() {
        OrientationRequest request = new OrientationRequest();
        OrientationResponse response = request.sendTo(endpoint);
        return response.orientation();
    }

    @Override
    public boolean ping() {
        PingRequest request = new PingRequest();
        PingResponse response = request.sendTo(endpoint);
        return response.responded();
    }

    @Override
    public void typeIntoKeyboard(String text) {
        TypeIntoKeyboardRequest request = new TypeIntoKeyboardRequest(text, json);
        request.sendTo(endpoint);
    }
}

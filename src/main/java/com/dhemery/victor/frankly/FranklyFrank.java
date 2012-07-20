package com.dhemery.victor.frankly;

import com.dhemery.network.Codec;
import com.dhemery.network.Endpoint;
import com.dhemery.victor.frank.Frank;

import java.util.List;

/**
 * <p>
 * A Frank agent that speaks Frankly through an Endpoint.
 * </p>
 */
public class FranklyFrank implements Frank {
    private static final String ACCESSIBILITY_CHECK_REQUEST = "accessibility_check";
    private static final String DUMP_REQUEST = "dump";
    private static final String ORIENTATION_REQUEST = "orientation";
    private static final String APP_EXEC_REQUEST = "app_exec";
    private static final String MAP_REQUEST = "map";
    private static final String TYPE_INTO_KEYBOARD_REQUEST = "type_into_keyboard";
    private final Endpoint endpoint;
    private final Codec codec;

    /**
     * Create a Frank agent to communicate with the Frank server at the given endpoint.
     * @param endpoint the endpoint at which the Frank server can be accessed.
     * @param codec codec to serialize Frankly payloads and deserialize Frankly responses.
     */
    public FranklyFrank(Endpoint endpoint, Codec codec) {
        this.endpoint = endpoint;
        this.codec = codec;
    }

    @Override
    public boolean accessibilityCheck() {
        AccessibilityCheckResponse response = get(ACCESSIBILITY_CHECK_REQUEST, AccessibilityCheckResponse.class);
        return response.accessibilityEnabled;
    }

    @Override
    public String appExec(String name, Object...arguments) {
        Operation operation = new Operation(name, arguments);
        MessageResponse response = put(APP_EXEC_REQUEST, operation, MessageResponse.class);
        return response.results.get(0);
    }

    @Override
    public String dump() {
        return get(DUMP_REQUEST);
    }

    @Override
    public List<String> map(String engine, String query, String name, Object...arguments) {
        Operation operation = new Operation(name, arguments);
        MapOperation mapOperation = new MapOperation(engine, query, operation);
        MessageResponse response = put(MAP_REQUEST, mapOperation, MessageResponse.class);
        return response.results;
    }

    @Override
    public String orientation() {
        OrientationResponse response = get(ORIENTATION_REQUEST, OrientationResponse.class);
        return response.orientation;
    }

    @Override
    public void typeIntoKeyboard(String text) {
        TextToType textToType = new TextToType(text);
        put(TYPE_INTO_KEYBOARD_REQUEST, textToType, MessageResponse.class);
    }

    @Override
    public Endpoint endpoint() {
        return endpoint;
    }

    private String get(String path) {
        return endpoint.get(path);
    }

    private <T> T get(String path, Class<T> responseType) {
        String rawResponse = get(path);
        return codec.decode(rawResponse, responseType);
    }

    private <T> T put(String path, Object payload, Class<T> responseType) {
        String message = codec.encode(payload);
        String rawResponse = endpoint.put(path, message);
        return codec.decode(rawResponse, responseType);
    }
}

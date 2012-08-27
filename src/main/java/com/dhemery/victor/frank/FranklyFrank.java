package com.dhemery.victor.frank;

import com.dhemery.network.SerializingEndpoint;
import com.dhemery.victor.frank.frankly.*;

import java.util.List;

/**
 * <p>
 * A Frank agent that speaks Frankly through an Endpoint.
 * </p>
 */
public class FranklyFrank implements Frank {
    private static final String ACCESSIBILITY_CHECK_REQUEST = "/accessibility_check";
    private static final String DUMP_REQUEST = "/dump";
    private static final String ORIENTATION_REQUEST = "/orientation";
    private static final String APP_EXEC_REQUEST = "/app_exec";
    private static final String MAP_REQUEST = "/map";
    private static final String TYPE_INTO_KEYBOARD_REQUEST = "/type_into_keyboard";
    private final SerializingEndpoint endpoint;

    /**
     * Create a Frank agent to communicate with the Frank server at the given endpoint.
     * @param endpoint the endpoint at which the Frank server can be accessed.
     */
    public FranklyFrank(SerializingEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public boolean accessibilityCheck() {
        AccessibilityCheckResponse response = get(ACCESSIBILITY_CHECK_REQUEST, AccessibilityCheckResponse.class);
        return response.enabled();
    }

    @Override
    public String appExec(String name, Object...arguments) {
        Operation operation = new Operation(name, arguments);
        AppExecOperation appExecOperation = new AppExecOperation(operation);
        MessageResponse response = put(APP_EXEC_REQUEST, appExecOperation, MessageResponse.class);

        if(response.succeeded()) return response.results().get(0);

        throw new IosOperationException("Application delegate", operation, response);
    }

    @Override
    public String dump() {
        return get(DUMP_REQUEST, String.class);
    }

    @Override
    public List<String> map(String engine, String query, String name, Object...arguments) {
        Operation operation = new Operation(name, arguments);
        MapOperation mapOperation = new MapOperation(engine, query, operation);
        MessageResponse response = put(MAP_REQUEST, mapOperation, MessageResponse.class);

        if(response.succeeded()) return response.results();

        throw new IosOperationException(engine + ' ' + query, operation, response);
    }

    @Override
    public String orientation() {
        OrientationResponse response = get(ORIENTATION_REQUEST, OrientationResponse.class);
        return response.orientation();
    }

    @Override
    public void typeIntoKeyboard(String text) {
        TextToType textToType = new TextToType(text);
        put(TYPE_INTO_KEYBOARD_REQUEST, textToType, Void.class);
    }

    @Override
    public SerializingEndpoint endpoint() {
        return endpoint;
    }

    private <T> T get(String path, Class<T> responseType) {
        return endpoint.get(path, responseType);
    }

    private <T> T put(String path, Object payload, Class<T> responseType) {
        return endpoint.put(path, payload, responseType);
    }

    @Override
    public String toString() {
        return "Frank at " + endpoint;
    }
}

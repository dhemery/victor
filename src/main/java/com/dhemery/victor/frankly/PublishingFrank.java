package com.dhemery.victor.frankly;

import com.dhemery.network.Codec;
import com.dhemery.network.Endpoint;
import com.dhemery.victor.frank.Frank;
import com.dhemery.victor.frank.FrankSubscriber;

import java.util.List;

/**
 * <p>
 * A Frank agent that publishes each Frank request and response.
 * </p>
 * <p>
 * This table summarizes how {@code FranklyFrank} translates method calls into Frankly messages:
 * </p>
 * <table>
 *     <tr>
 *         <th>Method</th>
 *         <th>Franky Message</th>
 *         <th>Payload Type</th>
 *         <th>Result Type</th>
 *     </tr>
 *     <tr>
 *         <td>{@link #accessibilityCheck()}</td>
 *         <td>{@code "accessibility_check"}</td>
 *         <td>none</td>
 *         <td>{@link AccessibilityCheckResponse}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #appExec(String, Object...)}</td>
 *         <td>{@code "app_exec"}</td>
 *         <td>{@link Operation}</td>
 *         <td>{@link MessageResponse}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #dump()}</td>
 *         <td>{@code "dump"}</td>
 *         <td>none</td>
 *         <td>String</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #map(String, String, String, Object...)}</td>
 *         <td>{@code "map"}</td>
 *         <td>{@link MapOperation}</td>
 *         <td>{@link MessageResponse}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #orientation()}</td>
 *         <td>{@code "orientation"}</td>
 *         <td>none</td>
 *         <td>{@link String}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #typeIntoKeyboard(String)}</td>
 *         <td>{@code "type_into_keyboard"}</td>
 *         <td>{@link TextToType}</td>
 *         <td>none</td>
 *     </tr>
 * </table>
 * <p>
 * Messages that carry a payload are sent using HTTP PUT.
 * Messages that do not carry a payload are sent using HTTP GET.
 * </p>
 * <p>
 * The {@code ping} method sends the {@code "accessibility_check"} message
 * because it's a cheap way to test whether the server responds.
 * </p>
 */
public class PublishingFrank implements Frank {
    private static final String ACCESSIBILITY_CHECK_REQUEST = "accessibility_check";
    private static final String DUMP_REQUEST = "dump";
    private static final String ORIENTATION_REQUEST = "orientation";
    private static final String APP_EXEC_REQUEST = "app_exec";
    private static final String MAP_REQUEST = "map";
    private static final String TYPE_INTO_KEYBOARD_REQUEST = "type_into_keyboard";
    private final FrankSubscriber publish;
    private final Endpoint endpoint;
    private final Codec codec;

    /**
     * Create a Frank agent to communicate with the Frank server at the given endpoint.
     * @param endpoint the endpoint at which the Frank server can be accessed.
     * @param codec codec to serialize Frankly payloads and deserialize Frankly responses.
     */
    public PublishingFrank(FrankSubscriber subscriber, Endpoint endpoint, Codec codec) {
        publish = subscriber;
        this.endpoint = endpoint;
        this.codec = codec;
    }

    @Override
    public boolean accessibilityCheck() {
        publish.accessibilityCheckRequest();
        AccessibilityCheckResponse response = get(ACCESSIBILITY_CHECK_REQUEST, AccessibilityCheckResponse.class);
        boolean accessibilityEnabled = response.accessibilityEnabled();
        publish.accessibilityCheckResponse(response);
        return accessibilityEnabled;
    }

    @Override
    public String appExec(String name, Object...arguments) {
        publish.appExecRequest(name, arguments);
        Operation operation = new Operation(name, arguments);
        MessageResponse response = put(APP_EXEC_REQUEST, operation, MessageResponse.class);
        publish.appExecResponse(name, arguments, response);
        return response.results().get(0);
    }

    @Override
    public String dump() {
        publish.dumpRequest();
        String response = get(DUMP_REQUEST);
        publish.dumpResponse(response);
        return response;
    }

    @Override
    public List<String> map(String engine, String query, String name, Object...arguments) {
        publish.mapRequest(engine, query, name, arguments);
        Operation operation = new Operation(name, arguments);
        MapOperation mapOperation = new MapOperation(engine, query, operation);
        MessageResponse response = put(MAP_REQUEST, mapOperation, MessageResponse.class);
        publish.mapResponse(engine, query, name, arguments, response);
        return response.results();
    }

    @Override
    public String orientation() {
        publish.orientationRequest();
        OrientationResponse response = get(ORIENTATION_REQUEST, OrientationResponse.class);
        publish.orientationResponse(response);
        return response.orientation();
    }

    @Override
    public void typeIntoKeyboard(String text) {
        publish.typeIntoKeyboardRequest(text);
        TextToType textToType = new TextToType(text);
        put(TYPE_INTO_KEYBOARD_REQUEST, textToType, MessageResponse.class);
        publish.typeIntoKeyboardResponse();
    }

    @Override
    public Endpoint endpoint() {
        return endpoint;
    }

    private String get(String path) {
        return endpoint.get(path);
    }

    private <T> T get(String request, Class<T> responseType) {
        String rawResponse = get(request);
        return codec.decode(rawResponse, responseType);
    }

    private <T> T put(String path, Object payload, Class<T> responseType) {
        String message = codec.encode(payload);
        String rawResponse = endpoint.put(path, message);
        return codec.decode(rawResponse, responseType);
    }
}

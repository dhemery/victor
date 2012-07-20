package com.dhemery.victor.frankly;

import com.dhemery.network.Codec;
import com.dhemery.network.Endpoint;
import com.dhemery.victor.frank.Frank;
import com.google.common.eventbus.EventBus;

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
    private final EventBus publisher;
    private final Endpoint endpoint;
    private final Codec codec;

    /**
     * Create a Frank agent to communicate with the Frank server at the given endpoint.
     * @param endpoint the endpoint at which the Frank server can be accessed.
     * @param codec codec to serialize Frankly payloads and deserialize Frankly responses.
     */
    public PublishingFrank(EventBus publisher, Endpoint endpoint, Codec codec) {
        this.publisher = publisher;
        this.endpoint = endpoint;
        this.codec = codec;
    }

    @Override
    public boolean accessibilityCheck() {
        publisher.post(new FrankEvent.WillRequestAccessibilityCheck());
        AccessibilityCheckResponse response = get(ACCESSIBILITY_CHECK_REQUEST, AccessibilityCheckResponse.class);
        boolean accessibilityEnabled = response.accessibilityEnabled;
        publisher.post(new FrankEvent.AccessibilityCheckReturned(accessibilityEnabled));
        return accessibilityEnabled;
    }

    @Override
    public String appExec(String name, Object...arguments) {
        publisher.post(new FrankEvent.WillRequestAppExec(name, arguments));
        Operation operation = new Operation(name, arguments);
        MessageResponse response = put(APP_EXEC_REQUEST, operation, MessageResponse.class);
        String result = response.results.get(0);
        publisher.post(new FrankEvent.AppExecReturned(name, arguments, result));
        return result;
    }

    @Override
    public String dump() {
        publisher.post(new FrankEvent.WillRequestDump());
        String response = get(DUMP_REQUEST);
        publisher.post(new FrankEvent.DumpReturned(response));
        return response;
    }

    @Override
    public List<String> map(String engine, String query, String name, Object...arguments) {
        publisher.post(new FrankEvent.WillRequestMap(engine, query, name, arguments));
        Operation operation = new Operation(name, arguments);
        MapOperation mapOperation = new MapOperation(engine, query, operation);
        MessageResponse response = put(MAP_REQUEST, mapOperation, MessageResponse.class);
        List<String> results = response.results;
        publisher.post(new FrankEvent.MapReturned(engine, query, name, arguments, results));
        return results;
    }

    @Override
    public String orientation() {
        publisher.post(new FrankEvent.WillRequestOrientation());
        OrientationResponse response = get(ORIENTATION_REQUEST, OrientationResponse.class);
        String orientation = response.orientation;
        publisher.post(new FrankEvent.OrientationReturned(orientation));
        return orientation;
    }

    @Override
    public void typeIntoKeyboard(String text) {
        publisher.post(new FrankEvent.WillRequestTypeIntoKeyboard(text));
        TextToType textToType = new TextToType(text);
        MessageResponse response = put(TYPE_INTO_KEYBOARD_REQUEST, textToType, MessageResponse.class);
        publisher.post(new FrankEvent.TypeIntoKeyboardReturned());
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

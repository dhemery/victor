package com.dhemery.victor.frankly;

import com.dhemery.victor.frank.Frank;
import com.dhemery.network.*;

import java.util.List;

/**
 * <p>
 * A Frank agent that sends and receives messages using the Frankly wire protocol.
 * For a description of the Frankly wire protocol, see the Frank website.
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
 *         <td>{@link #appExec}</td>
 *         <td>{@code "app_exec"}</td>
 *         <td>{@link Operation}</td>
 *         <td>{@link MessageResponse}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #map}</td>
 *         <td>{@code "map"}</td>
 *         <td>{@link MapOperation}</td>
 *         <td>{@link MessageResponse}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #orientation}</td>
 *         <td>{@code "orientation"}</td>
 *         <td>none</td>
 *         <td>{@link String}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #ping}</td>
 *         <td>{@code "ping"}</td>
 *         <td>none</td>
 *         <td>{@code boolean}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #typeIntoKeyboard}</td>
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
 * Note that {@code "ping"} is not a valid Frankly message.
 * {@code FranklyFrank} sends {@code "ping"} only to generate an HTTP response
 * to determine whether the Frank server responds to messages.
 * </p>
 */
public class FranklyFrank implements Frank {
    private static final String ORIENTATION_REQUEST = "orientation";
    private static final String PING_REQUEST = "ping";
    private static final String APP_EXEC = "app_exec";
    private static final String MAP = "map";
    private static final String TYPE_INTO_KEYBOARD = "type_into_keyboard";
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

    @Override public Endpoint endpoint() {
        return endpoint;
    }

    private String get(String path) {
        return endpoint.get(path);
    }

    private MessageResponse put(String path, Object payload) {
        String message = codec.encode(payload);
        String response = endpoint.put(path, message);
        return codec.decode(response, MessageResponse.class);
    }
}

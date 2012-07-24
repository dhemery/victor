package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.frank.Frank;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Represents a message to send to a set of objects in an iOS application.
 * {@code Operation} is a serializable representation
 * of the argument to {@link Frank#appExec}
 * and of the final two arguments to {@link Frank#map}.
 * </ul>
 * @see com.dhemery.victor.frank.FranklyFrank
 */
public class Operation {
    @SerializedName("method_name")
    private final String method;
    private final List<Object> arguments;

    /**
     * Create a message to send to a set of objects.
     * @param method the name of the message to send.
     * @param arguments the arguments to send with the message.
     */
    public Operation(String method, Object... arguments) {
        this.method = method;
        this.arguments = Collections.unmodifiableList(Arrays.asList(arguments));
    }

    public String method() { return method; }
    public List<Object> arguments() { return arguments; }
}

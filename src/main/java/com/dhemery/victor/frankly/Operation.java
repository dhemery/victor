package com.dhemery.victor.frankly;

import com.dhemery.victor.frank.Frank;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Represents a message to send to a set of objects in an iOS application.
 * {@code Operation} is a serializable representation
 * of the argument to {@link Frank#appExec}
 * and of the final two arguments to {@link Frank#map}.
 * </ul>
 * @see PublishingFrank
 */
public class Operation implements Serializable {
    private final String method;
    private final Object[] arguments;

    /**
     * Create a message to send to a set of objects.
     * @param method the name of the message to send.
     * @param arguments the arguments to send with the message.
     */
    public Operation(String method, Object... arguments) {
        this.method = method;
        this.arguments = arguments;
    }

    /**
     * The name of the message to send.
     */
    public String method() { return method; }

    /**
     * The arguments to send with the message.
     */
    public List<Object> arguments() { return Arrays.asList(arguments); }
}

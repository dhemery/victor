package com.dhemery.victor.frankly;

import com.dhemery.victor.frank.Frank;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * <p>
 * Represents a message to send to a set of objects in an iOS application.
 * {@code Operation} is a serializable representation
 * of the argument to {@link Frank#appExec}
 * and of the final two arguments to {@link Frank#map}.
 * </ul>
 * @see FranklyFrank
 */
public class Operation {
    public final String method;
    public final List<Object> arguments;

    /**
     * Create a message to send to a set of objects.
     * @param method the name of the message to send.
     * @param arguments the arguments to send with the message.
     */
    public Operation(String method, Object... arguments) {
        this.method = method;
        this.arguments = ImmutableList.copyOf(arguments);
    }
}

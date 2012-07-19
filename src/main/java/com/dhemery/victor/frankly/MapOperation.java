package com.dhemery.victor.frankly;

import com.dhemery.victor.frank.Frank;

import java.io.Serializable;

/**
 * An operation to send a message to a set of views.
 * {@code MapOperation} is the serializable representation
 * of the arguments to {@link Frank#map}.
 * @see PublishingFrank
 */
public class MapOperation implements Serializable {
    private final String selector_engine;
    private final String query;
    private final Operation operation;

    /**
     * Create an operation to send a message to a set of views.
     * @param engine the name of the selector engine to select recipients
     * @param query the pattern for the selector engine to use to select recipients
     * @param operation the message to send to each recipient
     */
    public MapOperation(String engine, String query, Operation operation) {
        selector_engine = engine;
        this.query = query;
        this.operation = operation;
    }

    /**
     * The name of the selector engine that will select recipients for the message.
     */
    public String engine() { return selector_engine; }

    /**
     * The pattern that the selector engine will use to select recipients for the message.
     */
    public String query() { return query; }

    /**
     * The message to send to each recipient selected by the selector engine.
     */
    public Operation operation() { return operation; }
}

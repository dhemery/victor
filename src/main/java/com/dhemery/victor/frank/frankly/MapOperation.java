package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.frank.Frank;
import com.google.gson.annotations.SerializedName;

/**
 * An operation to send a message to a set of views.
 * {@code MapOperation} is the serializable representation
 * of the arguments to {@link Frank#map}.
 * @see com.dhemery.victor.frank.FranklyFrank
 */
public class MapOperation {
    /**
     * The name of the selector engine that will select recipients for the message.
     */
    @SerializedName("selector_engine")
    private final String engine;

    /**
     * The pattern that the selector engine will use to select recipients for the message.
     */
    private final String query;

    /**
     * The message to send to each recipient selected by the selector engine.
     */
    private final Operation operation;

    /**
     * Create an operation to send a message to a set of views.
     * @param engine the name of the selector engine to select recipients
     * @param query the pattern for the selector engine to use to select recipients
     * @param operation the message to send to each recipient
     */
    public MapOperation(String engine, String query, Operation operation) {
        this.engine = engine;
        this.query = query;
        this.operation = operation;
    }

    public String engine() { return engine; }
    public String query() { return query; }
    public Operation operation() { return operation; }
}

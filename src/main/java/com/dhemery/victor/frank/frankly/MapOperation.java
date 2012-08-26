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

    @Override
    public String toString() {
        return engine + ' ' + query + ' ' + operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapOperation that = (MapOperation) o;

        if (engine != null ? !engine.equals(that.engine) : that.engine != null) return false;
        if (operation != null ? !operation.equals(that.operation) : that.operation != null) return false;
        if (query != null ? !query.equals(that.query) : that.query != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = engine != null ? engine.hashCode() : 0;
        result = 31 * result + (query != null ? query.hashCode() : 0);
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        return result;
    }
}

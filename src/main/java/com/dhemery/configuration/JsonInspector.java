package com.dhemery.configuration;

import java.util.Set;

/**
 * Retrieves information from a JSON representation of an object.
 */
public interface JsonInspector {
    /**
     * The names of the name/value pairs in a JSON object.
     * @param path the path to the JSON object
     */
    Set<String> names(Object... path);

    /**
     * The number of values in a JSON array.
     * @param path the path to the JSON array
     */
    Integer size(Object... path);

    /**
     * The string representation of a JSON value.
     * @param path the path to the JSON value
     */
    String stringValue(Object... path);
}

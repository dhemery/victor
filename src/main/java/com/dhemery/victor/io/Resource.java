package com.dhemery.victor.io;

/**
 * An identifiable network entity that responds to messages.
 */
public interface Resource {
    /**
     * Retrieve the content of the resource.
     */
    String get();

    /**
     * Send a message to the resource and return its response.
     */
    String put(String message);
}

package com.dhemery.network;

import java.net.URL;

/**
 * An identifiable network entity that responds to messages.
 */
public interface Resource {
    /**
     * The URL through which this resource can be accessed.
     */
    URL url();

    /**
     * Retrieve the content of the resource.
     */
    String get();

    /**
     * Send a message to the resource and return its response.
     */
    String put(String message);
}

package com.dhemery.victor.io;

/**
 * A service endpoint at which resources can be accessed.
 */
public interface Endpoint {
    /**
     * The service's host.
     */
    String host();

    /**
     * The port at which the service listens for requests.
     */
    int port();

    /**
     * Retrieves the content of the resource at the specified path within the endpoint.
     */
    String get(String path);

    /**
     * Sends a message to the resource at the specified path within the endpoint
     * and returns the resource's response.
     */
    String put(String path, String message);
}

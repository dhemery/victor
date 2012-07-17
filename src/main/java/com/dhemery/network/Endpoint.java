package com.dhemery.network;

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
     * Retrieve the content of the resource at the specified path within the endpoint.
     */
    String get(String path);

    /**
     * Send a message to the resource at the specified path within the endpoint
     * and return the resource's response.
     */
    String put(String path, String message);
}

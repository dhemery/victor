package com.dhemery.victor.io;

/**
 * Creates connections to resources.
 */
public interface Router {
    /**
     * Create a resource at the specified host, port, and path.
     * Note that the location does not include the protocol used to access the resource.
     * @param host the host where the resource is located.
     * @param port the port at which the host listens for messages for the resource.
     * @param path the path to the resource on the host.
     */
    Resource resource(String host, int port, String path);
}

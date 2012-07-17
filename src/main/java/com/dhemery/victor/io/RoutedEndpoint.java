package com.dhemery.victor.io;

/**
 * An endpoint that uses a router to connect to resources.
 */
public class RoutedEndpoint implements Endpoint {
    private final Router router;
    private final String host;
    private final int port;

    /**
     * Create an routed endpoint at the given host and port.
     * @param router the router that locates resources for this endpoint.
     * @param host the endpoint's host.
     * @param port the endpoint's port.
     */
    public RoutedEndpoint(Router router, String host, int port) {
        this.router = router;
        this.host = host;
        this.port = port;
    }

    @Override
    public String host() {
        return host;
    }

    @Override
    public int port() {
        return port;
    }

    @Override
    public String get(String path) {
        return resource(path).get();
    }

    @Override
    public String put(String path, String message) {
        return resource(path).put(message);
    }

    private Resource resource(String path) {
        return router.resource(host, port, path);
    }
}

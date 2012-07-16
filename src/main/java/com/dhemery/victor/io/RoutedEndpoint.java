package com.dhemery.victor.io;

public class RoutedEndpoint implements Endpoint {
    private final Router router;
    private final String host;
    private final int port;

    public RoutedEndpoint(String host, int port, Router router) {
        this.router = router;
        this.host = host;
        this.port = port;
    }

    @Override
    public String get(String path) {
        Connection connection = router.connectionFor(host(), port(), path);
        connection.connect();
        String status = connection.responseStatus();
        String response = connection.responseBody();
        connection.disconnect();
        return response;
    }

    @Override
    public String put(String path, String body) {
        Connection connection = router.connectionFor(host(), port(), body);
        connection.connect();
        connection.write(body);
        String status = connection.responseStatus();
        String response = connection.responseBody();
        connection.disconnect();
        return response;
    }

    @Override public String host() { return host; }
    @Override public int port() { return port; }
    @Override public String protocol() { return router.protocol(); }
}

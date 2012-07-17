package com.dhemery.network;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A router that creates connections to {@link URLResource}s using a specified protocol.
 */
public class URLResourceRouter implements Router {
    private final String protocol;

    /**
     * Create a URL resource router for the specified protocol.
     */
    public URLResourceRouter(String protocol){
        this.protocol = protocol;
    }

    @Override
    public Resource resource(String host, int port, String path) {
        return new URLResource(url(host, port, path));
    }

    private URL url(String host, int port, String path) {
        try {
            return new URL(protocol, host, port, path);
        } catch (MalformedURLException cause) {
            String format = "Cannot create URL protocol %s host %s port %s path %s";
            String explanation = String.format(format, protocol, host, port, path);
            throw new NetworkKitException(explanation, cause);
        }
    }
}

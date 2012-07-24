package com.dhemery.network;

import java.net.MalformedURLException;
import java.net.URL;

public class URLResourceFactory implements ResourceFactory {
    @Override
    public Resource resource(String protocol, String host, int port, String path) {
        return new URLResource(url(protocol, host, port, path));
    }

    private URL url(String protocol, String host, int port, String path) {
        try {
            return new URL(protocol, host, port, path);
        } catch (MalformedURLException cause) {
            String format = "Cannot create URL protocol %s host %s port %s path %s";
            String explanation = String.format(format, protocol, host, port, path);
            throw new NetworkKitException(explanation, cause);
        }
    }
}

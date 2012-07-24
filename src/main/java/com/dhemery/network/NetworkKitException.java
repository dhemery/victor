package com.dhemery.network;

import java.net.URL;

/**
 * Reports that Victor had a problem communicating with a resource.
 */
public class NetworkKitException extends RuntimeException {
    public NetworkKitException(String explanation, Throwable cause) {
        super(explanation, cause);
    }

    public NetworkKitException(Resource resource, String explanation, Throwable cause) {
        this(resource.url(), explanation, cause);
    }

    public NetworkKitException(URL url, String explanation, Throwable cause) {
        this("URL " + url + ' ' + explanation, cause);
    }
}

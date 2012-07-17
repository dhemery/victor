package com.dhemery.network;

/**
 * Reports that Victor had a problem communicating with a resource.
 */
public class NetworkKitException extends RuntimeException {
    public NetworkKitException(String explanation, Throwable cause) {
        super(explanation, cause);
    }
}

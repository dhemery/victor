package com.dhemery.victor.io;

/**
 * Reports that Victor had a problem communicating with a resource.
 */
public class IOKitException extends RuntimeException {
    public IOKitException(String explanation, Throwable cause) {
        super(explanation, cause);
    }
}

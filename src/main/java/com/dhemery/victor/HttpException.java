package com.dhemery.victor;

/**
 * Reports that Victor had a problem while communicating over HTTP.
 */
public class HttpException extends RuntimeException {
    public HttpException(String explanation, Throwable cause) {
        super(explanation, cause);
    }
}

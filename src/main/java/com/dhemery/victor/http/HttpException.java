package com.dhemery.victor.http;


public class HttpException extends RuntimeException {
    public HttpException(Throwable cause) {
        super(cause);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }
}

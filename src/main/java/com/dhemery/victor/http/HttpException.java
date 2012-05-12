package com.dhemery.victor.http;


public class HttpException extends RuntimeException {
    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }
}

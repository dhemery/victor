package com.dhemery.victor;

public class HttpException extends RuntimeException {
    public HttpException(String explanation, Throwable cause) {
        super(explanation, cause);
    }
}

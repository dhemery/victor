package com.dhemery.victor.os;


public class OSCommandException extends RuntimeException {
    public OSCommandException(OSCommand command, Throwable cause) {
        super(String.format("Exception while executing command %s", command), cause);
    }
}

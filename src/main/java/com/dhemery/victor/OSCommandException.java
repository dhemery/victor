package com.dhemery.victor;


public class OSCommandException extends RuntimeException {
    public OSCommandException(OSCommand command, Throwable cause) {
        super(String.format("Exception while executing shell command %s", command.description()), cause);
    }
}

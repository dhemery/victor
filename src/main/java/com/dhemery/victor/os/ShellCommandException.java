package com.dhemery.victor.os;


public class ShellCommandException extends RuntimeException {
    public ShellCommandException(Command command, Throwable cause) {
        super(String.format("Exception while executing shell command %s", command), cause);
    }
}

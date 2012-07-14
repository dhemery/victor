package com.dhemery.victor.os;


public class CommandException extends RuntimeException {
    public CommandException(Command command, Throwable cause) {
        super(String.format("Exception while executing shell command %s", command.description()), cause);
    }
}

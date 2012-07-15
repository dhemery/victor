package com.dhemery.victor.os;


import com.dhemery.victor.OSCommand;

public class CommandException extends RuntimeException {
    public CommandException(OSCommand command, Throwable cause) {
        super(String.format("Exception while executing shell command %s", command.description()), cause);
    }
}

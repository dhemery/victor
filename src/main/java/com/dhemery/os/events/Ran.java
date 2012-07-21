package com.dhemery.os.events;

import com.dhemery.os.OSCommand;

/**
 * Indicates that an {@link com.dhemery.os.OSCommand} was executed.
 * By the time this event posts,
 * the process launched by the command
 * may or may not have terminated.
 */
public class Ran {
    private final OSCommand command;

    public Ran(OSCommand command) {
        this.command = command;
    }
    public OSCommand command() { return command; }
}

package com.dhemery.os.events;

import com.dhemery.os.OSCommand;

/**
 * Indicates that an {@link OSCommand} will run.
 */
public class WillRun {
    private final OSCommand command;

    public WillRun(OSCommand command) {
        this.command = command;
    }
    public OSCommand command() { return command; }
}

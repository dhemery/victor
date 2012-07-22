package com.dhemery.os.events;

import com.dhemery.os.OSCommand;

/**
 * Indicates that an {@link OSCommand} returned its output to a caller.
 * This event does not occur automatically merely because a command produces output.
 * It is posted only in response to a caller requesting the output.
 */
public class Returned {
    private final OSCommand command;
    private final String output;

    public Returned(OSCommand command, String output) {
        this.command = command;
        this.output = output;
    }
    public OSCommand command() { return command; }
    public String output() { return output; }
}

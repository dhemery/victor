package com.dhemery.os;

/**
 * A command that can be run.
 */
public interface RunnableCommand extends OSCommand {
    /**
     * Run the command.
     * The return value represents a process
     * that may or may not have terminated by the time this method returns.
     * @return the OSProcess in which the command was run
     */
    OSProcess run();
}

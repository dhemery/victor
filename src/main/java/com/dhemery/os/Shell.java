package com.dhemery.os;

/**
 * A shell that provides an environment in which to run OS commands.
 */
public interface Shell {
    /**
     * Create a command builder with the given description and path.
     * The path must be such that typing it on the command line would run the command.
     */
    OSCommandBuilder<RunnableCommand> command(String description, String path);
}

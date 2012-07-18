package com.dhemery.os;

/**
 * Subscribes to be informed of OSCommand execution events.
 */
public interface OSCommandSubscriber {
    /**
     * Report that a command will run.
     * @param command the command that will run.
     */
    void willRun(OSCommand command);

    /**
     * Report that a command started executing.
     * By the time this event is published,
     * the command's process may or may not have terminated.
     * @param command the command that started executing
     *
     */
    void started(OSCommand command);

    /**
     * Report the output written by a command.
     * @param command the command that wrote the output.
     * @param output the command's output
     */
    void returned(OSCommand command, String output);
}

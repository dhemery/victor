package com.dhemery.os.publishing;

import com.dhemery.os.OSCommandBuilder;
import com.dhemery.os.RunnableCommand;
import com.dhemery.os.Shell;
import com.dhemery.publishing.Publisher;

/**
 * A wrapper that adds {@link com.dhemery.os.OSCommandEvent} publication
 * to the commands created by another shell.
 */
public class PublishingShell implements Shell {
    private final Publisher<Object> publisher;
    private final Shell shell;

    /**
     * Create a wrapper that publishes events about the commands created by the wrapped shell.
     * @param publisher the publisher through which to publish events
     * @param shell the underlying shell that creates the commands to run
     */
    public PublishingShell(Publisher<Object> publisher, Shell shell) {
        this.publisher = publisher;
        this.shell = shell;
    }

    @Override
    public OSCommandBuilder<RunnableCommand> command(String description, String path) {
        return new PublishingCommandBuilder(publisher, shell.command(description, path));
    }
}

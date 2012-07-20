package com.dhemery.os;

import com.dhemery.publishing.Publisher;

public class PublishedShell implements Shell {
    private final Publisher<Object> publisher;
    private final Shell shell;

    public PublishedShell(Publisher<Object> publisher, Shell shell) {
        this.publisher = publisher;
        this.shell = shell;
    }

    @Override
    public OSCommandBuilder<RunnableCommand> command(String description, String path) {
        return new PublishedCommandBuilder(publisher, shell.command(description, path));
    }
}

package com.dhemery.os;

import com.google.common.eventbus.EventBus;

public class PublishedShell implements Shell {
    private final EventBus eventBus;
    private final Shell shell;

    public PublishedShell(EventBus eventBus, Shell shell) {
        this.eventBus = eventBus;
        this.shell = shell;
    }

    @Override
    public OSCommandBuilder<RunnableCommand> command(String description, String path) {
        return new PublishedCommandBuilder(eventBus, shell.command(description, path));
    }
}

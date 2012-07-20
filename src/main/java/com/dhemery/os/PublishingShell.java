package com.dhemery.os;

import com.google.common.eventbus.EventBus;

import java.io.IOException;

public class PublishingShell implements Shell {
    private final EventBus publisher;

    public PublishingShell(EventBus publisher) {
        this.publisher = publisher;
    }

    @Override
    public OSProcess run(OSCommand command) {
        publisher.post(new OSCommandEvent.WillRun(command));
        Process process = null;
        try {
            process = build(command).start();
        } catch (IOException cause) {
            throw new OSCommandException(command, cause);
        }
        publisher.post(new OSCommandEvent.Ran(command));
        return new PublishingOSProcess(command, process, publisher);
    }

    private ProcessBuilder build(OSCommand command) {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command().add(command.path);
        builder.command().addAll(command.arguments);
        builder.environment().putAll(command.environment);
        return builder;
    }
}

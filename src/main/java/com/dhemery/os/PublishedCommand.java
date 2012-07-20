package com.dhemery.os;

import com.google.common.eventbus.EventBus;

import java.util.List;
import java.util.Map;

public class PublishedCommand implements RunnableCommand {
    private final EventBus publisher;
    private final RunnableCommand command;

    public PublishedCommand(EventBus publisher, RunnableCommand command) {
        this.publisher = publisher;
        this.command = command;
    }

    @Override
    public String path() {
        return command.path();
    }

    @Override
    public List<String> arguments() {
        return command.arguments();
    }

    @Override
    public Map<String, String> environment() {
        return command.environment();
    }

    @Override
    public String description() {
        return command.description();
    }

    @Override
    public OSProcess run() {
        publisher.post(new OSCommandEvent.WillRun(command));
        OSProcess process = command.run();
        publisher.post(new OSCommandEvent.Ran(command));
        return new PublishedProcess(publisher, command, process);
    }
}

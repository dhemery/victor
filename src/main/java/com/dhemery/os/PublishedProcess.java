package com.dhemery.os;

import com.google.common.eventbus.EventBus;

public class PublishedProcess implements OSProcess {
    private final EventBus publisher;
    private final OSCommand command;
    private final OSProcess process;

    public PublishedProcess(EventBus publisher, OSCommand command, OSProcess process) {
        this.publisher = publisher;
        this.command = command;
        this.process = process;
    }

    @Override
    public String output() {
        String output = process.output();
        publisher.post(new OSCommandEvent.Returned(command, output));
        return output;
    }
}

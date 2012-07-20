package com.dhemery.os;

import com.dhemery.publishing.Publisher;

public class PublishedProcess implements OSProcess {
    private final Publisher<Object> publisher;
    private final OSCommand command;
    private final OSProcess process;

    public PublishedProcess(Publisher<Object> publisher, OSCommand command, OSProcess process) {
        this.publisher = publisher;
        this.command = command;
        this.process = process;
    }

    @Override
    public String output() {
        String output = process.output();
        publisher.publish(new OSCommandEvent.Returned(command, output));
        return output;
    }
}

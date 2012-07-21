package com.dhemery.os.publishing;

import com.dhemery.os.OSCommandFactory;
import com.dhemery.os.RunnableCommand;
import com.dhemery.os.RuntimeCommand;
import com.dhemery.publishing.Publisher;

import java.util.List;
import java.util.Map;

public class PublishingCommandFactory implements OSCommandFactory<RunnableCommand> {
    private final Publisher<Object> publisher;

    public PublishingCommandFactory(Publisher<Object> publisher) {
        this.publisher = publisher;
    }

    @Override
    public RunnableCommand command(String description, String path, List<String> arguments, Map<String, String> environment) {
        RunnableCommand command = new RuntimeCommand(description, path, arguments, environment);
        return new PublishingCommand(publisher, command);
    }
}

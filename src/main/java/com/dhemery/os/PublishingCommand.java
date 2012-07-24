package com.dhemery.os;

import com.dhemery.os.events.Ran;
import com.dhemery.os.events.WillRun;
import com.dhemery.publishing.Publisher;

import java.util.List;
import java.util.Map;

/**
 * Wraps a command and publishes events when it runs.
 * <p>Before each call to {@link #run()}, the wrapper publishes a {@link com.dhemery.os.events.WillRun} event.</p>
 * <p>After each call to {@code run()}, the wrapper publishes a {@link com.dhemery.os.events.Ran} event.</p>
 */
public class PublishingCommand implements RunnableCommand {
    private final Publisher publisher;
    private final RunnableCommand command;

    /**
     * Wrap the given command and publish events when it runs.
     * @param publisher the publisher through which to publish events
     * @param command the command to wrap
     */
    public PublishingCommand(Publisher publisher, RunnableCommand command) {
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
        publisher.publish(new WillRun(command));
        OSProcess process = command.run();
        publisher.publish(new Ran(command));
        return new PublishingProcess(publisher, command, process);
    }
}

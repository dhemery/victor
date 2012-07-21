package com.dhemery.os.publishing;

import com.dhemery.os.OSCommandBuilder;
import com.dhemery.os.RunnableCommand;
import com.dhemery.publishing.Publisher;

import java.util.List;
import java.util.Map;

/**
 * Builds commands that publish events about their execution.
 * The publishable commands are created by wrapping another builder's commands
 * in a publishing wrapper.
 * <p>
 * See {@link PublishingCommand} and {@link PublishingProcess}
 * for details what events are published and when.
 * </p>
 */
public class PublishingCommandBuilder implements OSCommandBuilder<RunnableCommand> {
    private final Publisher<Object> publisher;
    private final OSCommandBuilder<RunnableCommand> builder;

    /**
     * Create a builder that wraps another builder's commands in a publishing wrapper.
     * @param publisher the publisher to which to publish events about the commands
     * @param builder the builder that will create the underlying commands
     */
    public PublishingCommandBuilder(Publisher<Object> publisher, OSCommandBuilder<RunnableCommand> builder) {
        this.publisher = publisher;
        this.builder = builder;
    }

    @Override
    public OSCommandBuilder<RunnableCommand> withArgument(String argument) {
        builder.withArgument(argument);
        return this;
    }

    @Override
    public OSCommandBuilder<RunnableCommand> withArguments(String... arguments) {
        builder.withArguments(arguments);
        return this;
    }

    @Override
    public OSCommandBuilder<RunnableCommand> withArguments(List<String> arguments) {
        builder.withArguments(arguments);
        return this;
    }

    @Override
    public OSCommandBuilder<RunnableCommand> withEnvironment(Map<String, String> environment) {
        builder.withEnvironment(environment);
        return this;
    }

    @Override
    public RunnableCommand get() {
        return new PublishingCommand(publisher, builder.get());
    }
}

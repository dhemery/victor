package com.dhemery.os;

import com.dhemery.publishing.Publisher;

import java.util.List;
import java.util.Map;

public class PublishedCommandBuilder implements OSCommandBuilder<RunnableCommand> {
    private final Publisher<Object> publisher;
    private final OSCommandBuilder<RunnableCommand> builder;

    public PublishedCommandBuilder(Publisher<Object> publisher, OSCommandBuilder<RunnableCommand> builder) {
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
        return new PublishedCommand(publisher, builder.get());
    }
}

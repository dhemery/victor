package com.dhemery.os;

import java.util.*;

/**
 * A command builder that uses a factory to construct the commands.
 */
public class FactoryBasedCommandBuilder implements OSCommandBuilder<RunnableCommand> {
    private final OSCommandFactory<RunnableCommand> create;
    private final String description;
    private final String path;
    private final List<String> arguments = new ArrayList<String>();
    private final Map<String, String> environment = new HashMap<String, String>();

    /**
     * Create a command builder that constructs its command using a factory.
     * @param factory the factory to construct the command
     * @param description the description of the command
     * @param path the path to the command executable
     */
    public FactoryBasedCommandBuilder(OSCommandFactory<RunnableCommand> factory, String description, String path) {
        create = factory;
        this.description = description;
        this.path = path;
    }

    @Override
    public OSCommandBuilder<RunnableCommand> withArgument(String argument) {
        arguments.add(argument);
        return this;
    }

    @Override
    public OSCommandBuilder<RunnableCommand> withArguments(String... arguments) {
        this.arguments.addAll(Arrays.asList(arguments));
        return this;
    }

    @Override
    public OSCommandBuilder<RunnableCommand> withArguments(List<String> arguments) {
        this.arguments.addAll(arguments);
        return this;
    }

    @Override
    public OSCommandBuilder<RunnableCommand> withEnvironment(Map<String, String> environment) {
        this.environment.putAll(environment);
        return this;
    }

    @Override
    public RunnableCommand build() {
        return create.command(description, path, arguments, environment);
    }
}

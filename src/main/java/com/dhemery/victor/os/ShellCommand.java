package com.dhemery.victor.os;

import java.util.*;

/**
 * A command to be performed by the operating system.
 *
 * @author Dale Emery
 */
public class ShellCommand implements Command {
    private final String path;
    private final List<String> arguments = new ArrayList<String>();
    private final Map<String, String> environment = new HashMap<String, String>();

    /**
     * @param path      the file path of the command to perform.
     */
    public ShellCommand(String path) {
        this.path = path;
    }

    public ShellCommand withArgument(String argument) {
        arguments.add(argument);
        return this;
    }

    public ShellCommand withArguments(String... arguments) {
        this.arguments.addAll(Arrays.asList(arguments));
        return this;
    }

    public ShellCommand withArguments(List<String> arguments) {
        this.arguments.addAll(arguments);
        return this;
    }

    public ShellCommand withEnvironment(Map<String,String> environment) {
        this.environment.putAll(environment);
        return this;
    }

    public String path() {
        return path;
    }

    public List<String> arguments() {
        return Collections.unmodifiableList(arguments);
    }

    public Map<String,String> environment() {
        return Collections.unmodifiableMap(environment);
    }

    @Override
    public void buildTo(ProcessBuilder builder) {
        builder.command(path);
        builder.command().addAll(arguments);
        builder.environment().clear();
        builder.environment().putAll(environment);
    }
}

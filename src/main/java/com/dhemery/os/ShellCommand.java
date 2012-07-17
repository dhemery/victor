package com.dhemery.os;

import java.util.*;

/**
 * A command to be executed by the operating system.
 */
public class ShellCommand implements OSCommand {
    private static final String DEFAULT_DESCRIPTION = "(command)";
    private final String path;
    private final List<String> arguments = new ArrayList<String>();
    private final Map<String, String> environment = new HashMap<String, String>();
    private String description = DEFAULT_DESCRIPTION;

    /**
     * @param path the file path of the command to perform.
     */
    public ShellCommand(String path) {
        this.path = path;
    }

    public ShellCommand describedAs(String description) {
        this.description = description;
        return this;
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

    @Override
    public List<String> arguments() {
        return Collections.unmodifiableList(arguments);
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public Map<String,String> environment() {
        return Collections.unmodifiableMap(environment);
    }

    @Override
    public String path() {
        return path;
    }
}

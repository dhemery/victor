package com.dhemery.os;

import java.util.*;

/**
 * Builds a command to be executed by the operating system.
 * <p>
 * To build the command:
 * </p>
 * <ul>
 * <li>Call {@link #withArgument(String)}, {@link #withArguments(String...)}, and {@link #withArguments(java.util.List)}
 * any number of times to append command arguments.</li>
 * <li>Call {@link #withEnvironment(java.util.Map)}
 * any number of times to add a set of environment variables
 * to the command's execution environment.</li>
 * </ul>
 * <p>
 * The resulting argument list contains the accumulated arguments,
 * in the order they were added.
 * </p>
 * <p>
 * The default description of the command is {@code "(command)"}.
 * To provide a more meaningful description, call {@link #describedAs(String)}.
 * </p>
 * <p>
 * Many {@code ShellCommand} methods are "builder" methods.
 * Each builder method returns {@code this},
 * so you can chain calls in a fluent builder expression.
 * </p>
 */
public class ShellCommand implements OSCommand {
    private static final String DEFAULT_DESCRIPTION = "(command)";
    private final String path;
    private final List<String> arguments = new ArrayList<String>();
    private final Map<String, String> environment = new HashMap<String, String>();
    private String description = DEFAULT_DESCRIPTION;

    /**
     * Create a command builder to execute the file at the given path.
     * The path must be such that invoking it on the command line would execute the file.
     * The constructed builder has a default description
     * and no arguments or environment variables.
     * @param path the path to the command to execute
     */
    public ShellCommand(String path) {
        this.path = path;
    }

    /**
     * Set the command's description.
     * @param description the description
     * @return this command builder
     */
    public ShellCommand describedAs(String description) {
        this.description = description;
        return this;
    }

    /**
     * Append an argument to the command's argument list.
     * @param argument the argument to append
     * @return this command builder
     */
    public ShellCommand withArgument(String argument) {
        arguments.add(argument);
        return this;
    }

    /**
     * Append arguments to the command's argument list.
     * @param arguments the arguments to append
     * @return this command builder
     */
    public ShellCommand withArguments(String... arguments) {
        this.arguments.addAll(Arrays.asList(arguments));
        return this;
    }

    /**
     * Append arguments to the command's argument list.
     * @param arguments the arguments to append
     * @return this command builder
     */
    public ShellCommand withArguments(List<String> arguments) {
        this.arguments.addAll(arguments);
        return this;
    }

    /**
     * Merge environment variables into the command's execution environment.
     * If the given map defines a variable
     * that is already defined in this command builder's accumulated environment,
     * the accumulated value is replaced by the one in the given map.
     * @param environment a map containing the variables to add
     * @return this command builder
     */
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

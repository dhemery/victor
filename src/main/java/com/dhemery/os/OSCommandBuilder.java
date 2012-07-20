package com.dhemery.os;

import java.util.*;

/**
 * Builds a command to be executed by the operating system.
 * <p>
 * To build the command:
 * </p>
 * <ul>
 * <li>Call {@link #withArgument(String)}, {@link #withArguments(String...)}, and {@link #withArguments(List)}
 * any number of times to append command arguments.</li>
 * <li>Call {@link #withEnvironment(Map)}
 * any number of times to add a set of environment variables
 * to the command's execution environment.</li>
 * </ul>
 * <p>
 * The resulting argument list contains the accumulated arguments,
 * in the order they were added.
 * </p>
 * <p>
 * Each method except {@link #build} returns {@code this},
 * so you can chain calls in a fluent builder expression.
 * </p>
 */
public class OSCommandBuilder {
    private final String description;
    private final String path;
    private final List<String> arguments = new ArrayList<String>();
    private final Map<String, String> environment = new HashMap<String, String>();

    public OSCommandBuilder(String description, String path) {
        this.description = description;
        this.path = path;
    }

    /**
     * Append an argument to the command's argument list.
     * @param argument the argument to append
     * @return this command builder
     */
    public OSCommandBuilder withArgument(String argument) {
        arguments.add(argument);
        return this;
    }

    /**
     * Append arguments to the command's argument list.
     * @param arguments the arguments to append
     * @return this command builder
     */
    public OSCommandBuilder withArguments(String... arguments) {
        this.arguments.addAll(Arrays.asList(arguments));
        return this;
    }

    /**
     * Append arguments to the command's argument list.
     * @param arguments the arguments to append
     * @return this command builder
     */
    public OSCommandBuilder withArguments(List<String> arguments) {
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
    public OSCommandBuilder withEnvironment(Map<String, String> environment) {
        this.environment.putAll(environment);
        return this;
    }

    /**
     * Create a command from the current state of this builder.
     */
    public OSCommand build() {
        return new OSCommand(description, path, arguments, environment);
    }
}

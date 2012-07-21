package com.dhemery.os;

import com.google.common.base.Supplier;

import java.util.List;
import java.util.Map;

/**
 * Builds an {@link OSCommand} to be executed by the operating system.
 * How the builder obtains the descripton and path for the command
 * is left to implementors.
 * <p>
 * To build a command:
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
 * @param <T> the type of {@code OSCommand} created
 */
public interface OSCommandBuilder<T extends OSCommand> extends Supplier<T> {
    /**
     * Append an argument to the command's argument list.
     * @param argument the argument to append
     * @return this command builder
     */
    OSCommandBuilder<T> withArgument(String argument);

    /**
     * Append arguments to the command's argument list.
     * @param arguments the arguments to append
     * @return this command builder
     */
    OSCommandBuilder<T> withArguments(String... arguments);

    /**
     * Append arguments to the command's argument list.
     * @param arguments the arguments to append
     * @return this command builder
     */
    OSCommandBuilder<T> withArguments(List<String> arguments);

     /**
     * Merge environment variables into the builder's execution environment for the command.
     * If the given map defines a variable
     * that is already defined in the builder's accumulated environment,
     * the builder's value is replaced by the one in the given map.
     * @param environment a map containing the variables to add
     * @return this command builder
     */
    OSCommandBuilder<T> withEnvironment(Map<String, String> environment);
}

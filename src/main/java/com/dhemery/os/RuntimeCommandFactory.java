package com.dhemery.os;

import java.util.List;
import java.util.Map;

/**
 * A factory that produces {@link RunnableCommand}s.
 */
public class RuntimeCommandFactory implements OSCommandFactory<RunnableCommand> {
    /**
     * Create {@link RunnableCommand} from the given parameters.
     * @param description an informative description of the command
     * @param path the path to the command executable
     * @param arguments arguments to pass to the command
     * @param environment environment variables to add to the command's execution environment
     * @return the constructed runnable command
     */
    @Override
    public RunnableCommand command(String description, String path, List<String> arguments, Map<String, String> environment) {
        return new RuntimeCommand(description, path, arguments, environment);
    }
}

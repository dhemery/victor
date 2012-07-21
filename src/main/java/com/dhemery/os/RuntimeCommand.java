package com.dhemery.os;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A runnable command launched via a Java {@link ProcessBuilder}.
 */
public class RuntimeCommand implements RunnableCommand {
    private final String path;
    private final List<String> arguments;
    private final Map<String, String> environment;
    private final String description;

    public RuntimeCommand(String description, String path, List<String> arguments, Map<String, String> environment) {
        this.description = description;
        this.path = path;
        this.arguments = Collections.unmodifiableList(arguments);
        this.environment = Collections.unmodifiableMap(environment);
    }

    @Override
    public OSProcess run() {
        ProcessBuilder builder = build();
        try {
            return new RuntimeProcess(this, builder.start());
        } catch (IOException cause) {
            throw new OSCommandException(this, cause);
        }
    }

    private ProcessBuilder build() {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command().add(path);
        builder.command().addAll(arguments);
        builder.environment().putAll(environment);
        return builder;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public List<String> arguments() {
        return arguments;
    }

    @Override
    public Map<String, String> environment() {
        return environment;
    }
}

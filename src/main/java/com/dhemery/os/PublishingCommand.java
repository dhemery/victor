package com.dhemery.os;

import java.io.IOException;
import java.util.*;

public class PublishingCommand implements OSCommand {
    private final String path;
    private final List<String> arguments = new ArrayList<String>();
    private final Map<String, String> environment = new HashMap<String, String>();
    private final OSCommandSubscriber publish;
    private final String description;

    public PublishingCommand(OSCommandSubscriber publisher, String description, String path, List<String> arguments, Map<String, String> environment) {
        publish = publisher;
        this.description = description;
        this.path = path;
        this.arguments.addAll(arguments);
        this.environment.putAll(environment);
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
    public OSProcess run() {
        publish.willRun(this);
        Process process = start();
        publish.ran(this);
        return new PublishingProcess(publish, this, process);
    }

    private Process start() {
        ProcessBuilder builder = new ProcessBuilder().command(path);
        builder.command().addAll(arguments);
        builder.environment().putAll(environment);
        try {
            return builder.start();
        } catch (IOException cause) {
            throw new OSCommandException(this, cause);
        }
    }

    @Override
    public String path() {
        return path;
    }
}

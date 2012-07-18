package com.dhemery.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Executes {@code OSCommand}s and publishes events about their execution.
 */
public class Shell implements OSCommandPublisher {
    private final OSCommandRepublisher publish = new OSCommandRepublisher();

    /**
     * Run the given command.
     * Before running the command,
     * the shell publishes a {@link OSCommandSubscriber#willRun willRun} event.
     * After starting the command,
     * the shell publishes a {@link OSCommandSubscriber#started started} event.
     * The command may or may not have terminated before this method returns.
     * @param command the command to run
     * @return the {@link Process} created by running the command
     */
    public Process run(OSCommand command) {
        publish.willRun(command);
        try {
            Process process = processBuilderFor(command).start();
            publish.started(command);
            return process;
        } catch (IOException cause) {
            throw new OSCommandException(command, cause);
        }
    }

    /**
     * Run the given command (see {@link #run run}) and return the command's output.
     * Before returning, the shell publishes a {@link OSCommandSubscriber#returned returned} event
     * with the command's output.
     * @param command the command to run
     * @return the output from the command
     */
    public String outputFrom(OSCommand command) {
        Process process = run(command);
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String output = bufferedReader.readLine();
            publish.returned(command, output);
            return output;
        } catch (IOException cause) {
            throw new OSCommandException(command, cause);
        }
    }

    @Override
    public void subscribe(OSCommandSubscriber subscriber) {
        publish.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(OSCommandSubscriber unsubscribe) {
        publish.unsubscribe(unsubscribe);
    }

    private ProcessBuilder processBuilderFor(OSCommand command) {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command.path());
        builder.command().addAll(command.arguments());
        builder.environment().putAll(command.environment());
        return builder;
    }
}

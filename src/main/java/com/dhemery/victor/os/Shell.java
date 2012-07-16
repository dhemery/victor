package com.dhemery.victor.os;

import com.dhemery.victor.ListeningOSCommandPublisher;
import com.dhemery.victor.OSCommand;
import com.dhemery.victor.OSCommandSubscriber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Shell {
    private final ListeningOSCommandPublisher publish = new ListeningOSCommandPublisher();

    public Process run(OSCommand command) {
        publish.willRun(command);
        try {
            Process process = processBuilderFor(command).start();
            publish.started(command);
            return process;
        } catch (IOException cause) {
            throw new CommandException(command, cause);
        }
    }

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
            throw new CommandException(command, cause);
        }
    }

    public void addListener(OSCommandSubscriber listener) {
        publish.subscribe(listener);
    }

    public void removeListener(OSCommandSubscriber listener) {
        publish.unsubscribe(listener);
    }

    private ProcessBuilder processBuilderFor(OSCommand command) {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command.path());
        builder.command().addAll(command.arguments());
        builder.environment().putAll(command.environment());
        return builder;
    }
}
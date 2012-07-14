package com.dhemery.victor.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Shell {
    private final Set<CommandListener> listeners = new HashSet<CommandListener>();

    public Process run(Command command) {
        notifyWillRun(command);
        try {
            Process process = processBuilderFor(command).start();
            notifyStarted(command, process);
            return process;
        } catch (IOException cause) {
            throw new CommandException(command, cause);
        }
    }

    public String outputFrom(Command command) {
        Process process = run(command);
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String output = bufferedReader.readLine();
            notifyOutput(command, output);
            return output;
        } catch (IOException cause) {
            throw new CommandException(command, cause);
        }
    }

    public void addListener(CommandListener listener) {
        listeners.add(listener);
    }

    private void notifyWillRun(Command command) {
        for(CommandListener listener : listeners) {
            listener.willRun(command);
        }
    }

    private void notifyStarted(Command command, Process process) {
        for(CommandListener listener : listeners) {
            listener.started(command, process);
        }
    }

    private void notifyOutput(Command command, String output) {
        for(CommandListener listener : listeners) {
            listener.returned(command, output);
        }
    }

    public void removeListener(CommandListener listener) {
        listeners.remove(listener);
    }

    private ProcessBuilder processBuilderFor(Command command) {
        ProcessBuilder builder = new ProcessBuilder();
        command.buildTo(builder);
        return builder;
    }
}

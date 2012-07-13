package com.dhemery.victor.os;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Shell {
    private final Set<ShellListener> listeners = new HashSet<ShellListener>();

    public Process run(Command command) {
        notifyWillRun(command);
        try {
            Process process = processBuilderFor(command).start();
            notifyStarted(command, process);
            return process;
        } catch (IOException cause) {
            throw new ShellCommandException(command, cause);
        }
    }

    public String outputFrom(Command command) {
        String output = "(whoops)";
        notifyOutput(command, output);
        return output;
    }

    public void addListener(ShellListener listener) {
        listeners.add(listener);
    }

    private void notifyWillRun(Command command) {
        for(ShellListener listener : listeners) {
            listener.willRun(command);
        }
    }

    private void notifyStarted(Command command, Process process) {
        for(ShellListener listener : listeners) {
            listener.started(command, process);
        }
    }

    private void notifyOutput(Command command, String output) {
        for(ShellListener listener : listeners) {
            listener.returned(command, output);
        }
    }

    public void removeListener(ShellListener listener) {
        listeners.remove(listener);
    }

    private ProcessBuilder processBuilderFor(Command command) {
        ProcessBuilder builder = new ProcessBuilder();
        command.buildTo(builder);
        return builder;
    }
}

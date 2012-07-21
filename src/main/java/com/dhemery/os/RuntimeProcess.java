package com.dhemery.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A wrapper that adds an {@link #output()} method
 * to a Java process launched by an {@link OSCommand}.
 */
public class RuntimeProcess implements OSProcess {
    private final OSCommand command;
    private final Process process;

    /**
     * Wrap the given command and process.
     */
    public RuntimeProcess(OSCommand command, Process process) {
        this.command = command;
        this.process = process;
    }

    @Override
    public String output() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        try {
            return reader.readLine();
        } catch (IOException cause) {
            throw new OSCommandException(command, cause);
        }
    }
}

package com.dhemery.victor.os;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A command to be performed by the operating system.
 *
 * @author Dale Emery
 */
public class OSCommand {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ProcessBuilder builder;

    /**
     * @param path      the file path of the command to perform.
     * @param arguments arguments sent to the command.
     */
    public OSCommand(String path, List<String> arguments) {
        this(path, arguments, Collections.<String, String>emptyMap());
    }

    /**
     * @param path        the file path of the command to perform.
     * @param arguments   arguments sent to the command.
     * @param environment environment variables for the command.
     */
    public OSCommand(String path, List<String> arguments, Map<String, String> environment) {
        builder = new ProcessBuilder();
        builder.command().add(path);
        builder.command().addAll(arguments);
        builder.environment().putAll(environment);
    }

    /**
     * Run the command and return its output.
     * This method assumes that the command runs to completion
     * and writes a single line to stdout.
     *
     * @return the text that the command wrote to stdout.
     */
    @SuppressWarnings("IOResourceOpenedButNotSafelyClosed")
    public String output() {
        Process process = run();
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String output = bufferedReader.readLine();
            log.debug("   output: {}", output);
            return output;
        } catch (IOException cause) {
            throw new OSCommandException(this, cause);
        }
    }

    /**
     * Initiate the command.
     *
     * @return a native process that can describe and control the invoked command.
     */
    public Process run() {
        log.debug("Executing command {}", builder.command());
        try {
            return builder.start();
        } catch (IOException cause) {
            throw new OSCommandException(this, cause);
        }
    }

    @Override
    public String toString() {
        return String.format("command %s", builder.command());
    }
}

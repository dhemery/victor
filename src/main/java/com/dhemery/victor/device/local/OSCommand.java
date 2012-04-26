package com.dhemery.victor.device.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A command to be performed by the operating system.
 * @author Dale Emery
 *
 */
public class OSCommand {
	private final Logger log = LoggerFactory.getLogger(getClass());
    private final ProcessBuilder builder;

    /**
	 * @param path the file path of the command to perform.
	 */
	public OSCommand(String path) {
        this(path, Collections.<String>emptyList());
	}

    /**
	 * @param path the file path of the command to perform.
	 * @param arguments arguments sent to the command.
	 */
	public OSCommand(String path, List<String> arguments) {
        this(path, arguments, Collections.<String,String>emptyMap());
	}

	/**
	 * @param path the file path of the command to perform.
	 * @param arguments arguments sent to the command.
     * @param environment environment variables for the command.
	 */
    public OSCommand(String path, List<String> arguments, Map<String,String> environment) {
        builder = new ProcessBuilder();
        builder.command().add(path);
        builder.command().addAll(arguments);
        builder.environment().putAll(environment);
    }

    /**
	 * Initiate the command.
	 * @return a native process that can describe and control the invoked command.
	 */
	public Process run() {
		log.debug("Executing command {} with environment {}", builder.command(), builder.environment());
		try {
			return builder.start();
		} catch (IOException cause) {
			throw new OSCommandException(this, cause);
		}
	}

    @Override
    public String toString() {
        return String.format("Command %s with environment %s", builder.command(), builder.environment());
    }
}

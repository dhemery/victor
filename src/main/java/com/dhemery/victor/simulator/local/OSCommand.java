package com.dhemery.victor.simulator.local;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A command to be performed by the operating system.
 * @author Dale Emery
 *
 */
public class OSCommand {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final List<String> command = new ArrayList<String>();
    private final List<String> environment = new ArrayList<String>();

    /**
	 * @param path the file path of the command to perform.
	 * @param arguments arguments sent to the command.
	 */
	public OSCommand(String path, List<String> arguments) {
        this(path, arguments, new ArrayList<String>());
	}

	/**
	 * @param path the file path of the command to perform.
	 * @param arguments arguments sent to the command.
     * @param environment environment variables for the command.
	 */
    public OSCommand(String path, List<String> arguments, List<String> environment) {
        command.add(path);
        command.addAll(arguments);
        this.environment.addAll(environment);
    }

    /**
	 * Initiates the command.
	 * @return a native process that can describe and control the invoked command.
	 * @throws java.io.IOException
	 */
	public Process run() {
		log.debug("Executing command {} with environment {}", command, environment);
		try {
			return Runtime.getRuntime().exec(command.toArray(new String[command.size()]), environment.toArray(new String[environment.size()]));
		} catch (IOException e) {
			throw new OSCommandException(command.toString(), e);
		}
	}
}

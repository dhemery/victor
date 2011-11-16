package com.dhemery.victor.simulator;

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
	private final String[] command;

	/**
	 * @param path the file path of the command to perform.
	 * @param arguments arguments sent to the command.
	 */
	public OSCommand(String path, List<String> arguments) {
		List<String> commandElements = new ArrayList<String>();
		commandElements.add(path);
		commandElements.addAll(arguments);
		command = commandElements.toArray(new String[commandElements.size()]);		
	}

	/** 
	 * @param path the file path of the command to perform.
	 * @param arguments arguments sent to the command.
	 */
	public OSCommand(String path, String...arguments) {
		this(path, Arrays.asList(arguments));
	}

	/**
	 * Initiates the command.
	 * @return a native process that can describe and control the invoked command.
	 * @throws IOException
	 */
	public Process run() throws IOException {
		log.debug("Executing command {}", Arrays.asList(command));
		return Runtime.getRuntime().exec(command);
	}
}

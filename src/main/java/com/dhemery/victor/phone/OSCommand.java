package com.dhemery.victor.phone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSCommand {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final String[] command;
	
	public OSCommand(String path, List<String> arguments) {
		List<String> commandElements = new ArrayList<String>();
		commandElements.add(path);
		commandElements.addAll(arguments);
		command = commandElements.toArray(new String[commandElements.size()]);		
	}

	public OSCommand(String path, String...arguments) {
		this(path, Arrays.asList(arguments));
	}

	public Process run() throws IOException {
		log.debug("Executing command {}", Arrays.asList(command));
		return Runtime.getRuntime().exec(command);
	}
}

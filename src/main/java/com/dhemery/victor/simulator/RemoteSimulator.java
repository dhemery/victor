package com.dhemery.victor.simulator;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.victor.http.HttpRequest;


public class RemoteSimulator implements Simulator {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final String simulatorUrl;

	public RemoteSimulator(String simulatorUrl) {
		this.simulatorUrl = simulatorUrl;
		log.debug("Using remote simulator at {}", simulatorUrl);
	}

	public Simulator launch(String applicationPath) throws IOException {
		log.debug("Launching remote application {}", applicationPath);
		perform(new RemoteLaunchApplicationCommand(applicationPath));
		return this;
	}

	@Override
	public void shutDown() throws IOException, InterruptedException {
		perform(new RemoteCloseSimulatorCommand());
	}

	private void perform(HttpRequest operation) throws IOException {
		operation.sendTo(simulatorUrl);
	}

	@Override
	public void touchMenuItem(String menuName, String menuItemName) throws IOException, InterruptedException {
		perform(new RemoteTouchMenuItemCommand(menuName, menuItemName));
	}
}

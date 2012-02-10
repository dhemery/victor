package com.dhemery.victor.simulator.remote;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.victor.http.HttpRequest;
import com.dhemery.victor.simulator.Simulator;


public class RemoteSimulator implements Simulator {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final String simulatorUrl;

	public RemoteSimulator(String simulatorUrl) {
		this.simulatorUrl = simulatorUrl;
		log.debug("Using remote simulator at {}", simulatorUrl);
	}

	@Override
	public void launch(String applicationPath, String deviceType) throws IOException {
		log.debug("Launching remote application {}", applicationPath);
		perform(new LaunchApplicationRequest(applicationPath, deviceType));
	}

	@Override
	public void shutDown() throws IOException, InterruptedException {
		perform(new CloseSimulatorRequest());
	}

	private void perform(HttpRequest operation) throws IOException {
		operation.sendTo(simulatorUrl);
	}

	@Override
	public void touchMenuItem(String menuName, String menuItemName) throws IOException, InterruptedException {
		perform(new TouchMenuItemRequest(menuName, menuItemName));
	}
}

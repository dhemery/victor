package com.dhemery.victor.server;

import java.io.IOException;

import com.dhemery.victor.simulator.LocalSimulator;
import com.dhemery.victor.simulator.RemoteLaunchApplicationCommand;

public class LaunchApplicationHandler extends SimulatorExchangeHandler<RemoteLaunchApplicationCommand.Body> {
	public LaunchApplicationHandler(LocalSimulator simulator) {
		super(simulator, RemoteLaunchApplicationCommand.Body.class);
	}

	@Override
	public void perform(LocalSimulator simulator, RemoteLaunchApplicationCommand.Body body) throws IOException {
		simulator.launch(body.applicationPath);
	}
}

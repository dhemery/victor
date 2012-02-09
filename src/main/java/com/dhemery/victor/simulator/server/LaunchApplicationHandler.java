package com.dhemery.victor.simulator.server;

import java.io.IOException;

import com.dhemery.victor.simulator.local.LocalSimulator;
import com.dhemery.victor.simulator.remote.RemoteLaunchApplicationCommand;

public class LaunchApplicationHandler extends SimulatorExchangeHandler<RemoteLaunchApplicationCommand.Body> {
	public LaunchApplicationHandler(LocalSimulator simulator) {
		super(simulator, RemoteLaunchApplicationCommand.Body.class);
	}

	@Override
	public void perform(LocalSimulator simulator, RemoteLaunchApplicationCommand.Body body) throws IOException {
		simulator.launch(body.applicationPath);
	}
}

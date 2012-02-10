package com.dhemery.victor.simulator.server;

import java.io.IOException;

import com.dhemery.victor.simulator.local.LocalSimulator;
import com.dhemery.victor.simulator.remote.LaunchApplicationCommand;

public class LaunchApplicationHandler extends SimulatorExchangeHandler<LaunchApplicationCommand> {
	public LaunchApplicationHandler(LocalSimulator simulator) {
		super(simulator, LaunchApplicationCommand.class);
	}

	@Override
	public void perform(LocalSimulator simulator, LaunchApplicationCommand command) throws IOException {
		simulator.launch(command.applicationPath, command.deviceType);
	}
}

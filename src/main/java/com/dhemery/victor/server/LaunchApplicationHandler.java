package com.dhemery.victor.server;

import java.io.IOException;

import com.dhemery.victor.simulator.RemoteLaunchApplicationCommandBody;
import com.dhemery.victor.simulator.VictorOwnedSimulator;

public class LaunchApplicationHandler extends SimulatorExchangeHandler<RemoteLaunchApplicationCommandBody> {
	public LaunchApplicationHandler(VictorOwnedSimulator simulator) {
		super(simulator, RemoteLaunchApplicationCommandBody.class);
	}

	@Override
	public void perform(VictorOwnedSimulator simulator, RemoteLaunchApplicationCommandBody body) throws IOException {
		simulator.launch(body.applicationPath);
	}
}

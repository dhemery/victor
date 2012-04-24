package com.dhemery.victor.simulator.server;

import com.dhemery.victor.simulator.local.LocalSimulator;
import com.dhemery.victor.simulator.remote.LaunchApplicationMessage;

import java.io.IOException;

public class LaunchApplicationHandler extends SimulatorExchangeHandler<LaunchApplicationMessage> {
	public LaunchApplicationHandler(LocalSimulator simulator) {
		super(simulator, LaunchApplicationMessage.class);
	}

	@Override
	public void perform(LocalSimulator simulator, LaunchApplicationMessage message) throws IOException {
//		simulator.launch();
	}
}

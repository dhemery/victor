package com.dhemery.victor.simulator.server;

import java.io.IOException;

import com.dhemery.victor.simulator.local.LocalSimulator;

public class CloseSimulatorHandler extends SimulatorExchangeHandler<Void> {
	public CloseSimulatorHandler(LocalSimulator simulator) {
		super(simulator, Void.class);
	}

	@Override
	public void perform(LocalSimulator simulator, Void ignored) throws IOException, InterruptedException {
		simulator.shutDown();
	}
}

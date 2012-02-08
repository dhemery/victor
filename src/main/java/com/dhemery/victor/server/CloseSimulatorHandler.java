package com.dhemery.victor.server;

import java.io.IOException;

import com.dhemery.victor.simulator.VictorOwnedSimulator;

public class CloseSimulatorHandler extends SimulatorExchangeHandler<Void> {
	public CloseSimulatorHandler(VictorOwnedSimulator simulator) {
		super(simulator, Void.class);
	}

	@Override
	public void perform(VictorOwnedSimulator simulator, Void ignored) throws IOException, InterruptedException {
		simulator.shutDown();
	}
}

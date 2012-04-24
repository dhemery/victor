package com.dhemery.victor.device.server;

import com.dhemery.victor.device.LocalSimulator;

import java.io.IOException;

public class CloseSimulatorHandler extends SimulatorExchangeHandler<Void> {
	public CloseSimulatorHandler(LocalSimulator simulator) {
		super(simulator, Void.class);
	}

	@Override
	public void perform(LocalSimulator simulator, Void ignored) throws IOException, InterruptedException {
//		simulator.shutDown();
	}
}

package com.dhemery.victor.simulator.server;

import java.io.IOException;

import com.dhemery.victor.simulator.local.LocalSimulator;
import com.dhemery.victor.simulator.remote.RemoteTouchMenuItemCommand;

public class TouchMenuItemHandler extends SimulatorExchangeHandler<RemoteTouchMenuItemCommand.Body> {
	public TouchMenuItemHandler(LocalSimulator simulator) {
		super(simulator, RemoteTouchMenuItemCommand.Body.class);
	}

	@Override
	public void perform(LocalSimulator simulator, RemoteTouchMenuItemCommand.Body body) throws IOException, InterruptedException {
		simulator.touchMenuItem(body.menuName, body.menuItemName);
	}
}

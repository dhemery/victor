package com.dhemery.victor.simulator.server;

import java.io.IOException;

import com.dhemery.victor.simulator.local.LocalSimulator;
import com.dhemery.victor.simulator.remote.TouchMenuItemCommand;

public class TouchMenuItemHandler extends SimulatorExchangeHandler<TouchMenuItemCommand> {
	public TouchMenuItemHandler(LocalSimulator simulator) {
		super(simulator, TouchMenuItemCommand.class);
	}

	@Override
	public void perform(LocalSimulator simulator, TouchMenuItemCommand command) throws IOException, InterruptedException {
		simulator.touchMenuItem(command.menuName, command.menuItemName);
	}
}

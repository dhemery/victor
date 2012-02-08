package com.dhemery.victor.simulator;

import java.io.IOException;

public class AlreadyRunningSimulator implements Simulator {

	@Override
	public void shutDown() throws IOException, InterruptedException {
	}

	@Override
	public void touchMenuItem(String menuName, String menuItemName) throws IOException, InterruptedException {
		new TouchMenuItemCommand(menuName, menuItemName).run();
	}
}

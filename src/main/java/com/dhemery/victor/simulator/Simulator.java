package com.dhemery.victor.simulator;

import java.io.IOException;

public class Simulator {
	private final String simulatorPath;
	
	public Simulator(String simulatorPath) {
		this.simulatorPath = simulatorPath;
	}

	public void launch(String applicationPath) throws IOException {
		new OSCommand(simulatorPath, "-SimulateApplication", applicationPath).run();
	}

	public void shutDown() throws IOException, InterruptedException {
		touchMenuItem("iOS Simulator", "Quit iOS Simulator");
		waitForSimulatorToShutDown();
	}

	public void touchMenuItem(String menuName, String menuItemName) throws IOException, InterruptedException {
		new MenuTouchCommand(menuName, menuItemName).run().waitFor();
	}

	public void waitForSimulatorToShutDown() throws IOException, InterruptedException {
		new WaitUntilSimulatorShutsDownCommand().run().waitFor();
	}
}

package com.dhemery.victor.simulator;

import java.io.IOException;

public class LocalSimulator implements Simulator {
	private final String simulatorPath;
	private Process simulatorProcess;
	
	public LocalSimulator(String simulatorPath) {
		this.simulatorPath = simulatorPath;
	}

	@Override
	public Simulator launch(String applicationPath) throws IOException {
		simulatorProcess = new OSCommand(simulatorPath, "-SimulateApplication", applicationPath).run();
		return this;
	}

	@Override
	public void shutDown() throws IOException, InterruptedException {
		touchMenuItem("iOS Simulator", "Quit iOS Simulator");
		waitForSimulatorToShutDown();
	}

	@Override
	public Simulator touchMenuItem(String menuName, String menuItemName) throws IOException, InterruptedException {
		new MenuTouchCommand(menuName, menuItemName).run();
		return this;
	}

	private void waitForSimulatorToShutDown() throws InterruptedException {
		simulatorProcess.waitFor();
	}
}

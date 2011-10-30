package com.dhemery.victor.simulator;

import java.io.IOException;

public class LocalSimulator implements Simulator {
	private final String simulatorPath;
	private Process simulatorProcess;
	
	public LocalSimulator(String simulatorPath) {
		this.simulatorPath = simulatorPath;
	}

	@Override
	public void launch(String applicationPath) throws IOException {
		simulatorProcess = new OSCommand(simulatorPath, "-SimulateApplication", applicationPath).run();
	}

	@Override
	public void shutDown() throws IOException, InterruptedException {
		touchMenuItem("iOS Simulator", "Quit iOS Simulator");
		waitForSimulatorToShutDown();
	}

	@Override
	public void touchMenuItem(String menuName, String menuItemName) throws IOException, InterruptedException {
		new MenuTouchCommand(menuName, menuItemName).run();
	}

	private void waitForSimulatorToShutDown() throws InterruptedException {
		simulatorProcess.waitFor();
	}
}

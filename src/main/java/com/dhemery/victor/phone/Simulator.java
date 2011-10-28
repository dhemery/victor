package com.dhemery.victor.phone;

import java.io.IOException;

public class Simulator {
	private final String simulatorPath;
	private Process simulatorProcess;
	
	public Simulator(String simulatorPath) {
		this.simulatorPath = simulatorPath;
	}

	public void launch(String applicationPath) throws IOException {
		simulatorProcess = new OSCommand(simulatorPath, "-SimulateApplication", applicationPath).run();
	}

	public void shutDown() throws IOException, InterruptedException {
		touchMenuItem("iOS Simulator", "Quit iOS Simulator");
		waitForSimulatorToShutDown();
	}

	public void touchMenuItem(String menuName, String menuItemName) throws IOException, InterruptedException {
		new MenuTouchCommand(menuName, menuItemName).run();
	}

	public void waitForSimulatorToShutDown() throws InterruptedException {
		simulatorProcess.waitFor();
	}
}

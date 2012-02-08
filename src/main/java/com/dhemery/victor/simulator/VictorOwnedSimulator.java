package com.dhemery.victor.simulator;

import java.io.IOException;

/**
 * An iOS simulator running on this computer.
 * @author Dale Emery
 *
 */
public class VictorOwnedSimulator implements Simulator {
	private final String simulatorPath;
	private Process simulatorProcess;
	
	/**
	 * @param simulatorPath the file path to the simulator application.
	 */
	public VictorOwnedSimulator(String simulatorPath) {
		this.simulatorPath = simulatorPath;
	}

	public Simulator launch(String applicationPath) throws IOException {
		simulatorProcess = new OSCommand(simulatorPath, "-SimulateApplication", applicationPath).run();
		return this;
	}

	@Override
	public void shutDown() throws IOException, InterruptedException {
		if(simulatorProcess != null) {
			touchMenuItem("iOS Simulator", "Quit iOS Simulator");
			waitForSimulatorToShutDown();			
			simulatorProcess = null;
		}
	}

	@Override
	public void touchMenuItem(String menuName, String menuItemName) throws IOException, InterruptedException {
		new TouchMenuItemCommand(menuName, menuItemName).run();
	}

	private void waitForSimulatorToShutDown() throws InterruptedException {
		simulatorProcess.waitFor();
	}
}

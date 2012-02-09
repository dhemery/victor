package com.dhemery.victor.simulator.local;

import java.io.File;
import java.io.IOException;

import com.dhemery.victor.simulator.Simulator;

/**
 * An iOS simulator running on this computer.
 * @author Dale Emery
 *
 */
public class LocalSimulator implements Simulator {
	private final String simulatorPath;
	private Process simulatorProcess;
	
	/**
	 * @param simulatorPath the file path to the simulator application.
	 */
	public LocalSimulator(String simulatorPath) {
		this.simulatorPath = simulatorPath;
	}

	@Override
	public void launch(String applicationPath) throws IOException {
		String applicationAbsolutePath = new File(applicationPath).getAbsolutePath();
		simulatorProcess = new OSCommand(simulatorPath, "-SimulateApplication", applicationAbsolutePath).run();
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
		new LocalTouchMenuItemCommand(menuName, menuItemName).run();
	}

	private void waitForSimulatorToShutDown() throws InterruptedException {
		simulatorProcess.waitFor();
	}
}

package com.dhemery.victor.simulator.local;

import java.io.File;

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
	public void launch(String applicationPath, String deviceType, String sdkRoot) {
		String applicationAbsolutePath = new File(applicationPath).getAbsolutePath();
		simulatorProcess = new OSCommand(simulatorPath, "-SimulateApplication", applicationAbsolutePath, "-SimulateDevice", deviceType, "-currentSDKRoot", sdkRoot).run();
	}

	@Override
	public void shutDown() {
		if(simulatorProcess != null) {
			touchMenuItem("iOS Simulator", "Quit iOS Simulator");
			waitForSimulatorToShutDown();			
			simulatorProcess = null;
		}
	}

	@Override
	public void touchMenuItem(String menuName, String menuItemName) {
		new TouchMenuItemCommand(menuName, menuItemName).run();
	}

	private void waitForSimulatorToShutDown() {
		try {
			simulatorProcess.waitFor();
		} catch (InterruptedException e) {
			throw new SimulatorCommandException("Exception while waiting for simulator to shut down", e);
		}
	}
}

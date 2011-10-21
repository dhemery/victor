package com.dhemery.victor.driver;


public class SimulatorDriver {
	private static final String SHUTDOWN_SCRIPT = "application \"iPhone Simulator\" quit";
	private final String simulatorPath;
	
	public SimulatorDriver(String simulatorPath) {
		this.simulatorPath = simulatorPath;
	}

	public void launch(String appPath) {
		String[] launchCommand = new String[] { simulatorPath, "-SimulateApplication", appPath };
		run(launchCommand);
	}

	private void run(String[] command) {
		try {
			Runtime.getRuntime().exec(command);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public void shutDown() {
		String[] shutDownCommand = new String[] { "osascript", "-e", SHUTDOWN_SCRIPT};
		run(shutDownCommand);
	}
}

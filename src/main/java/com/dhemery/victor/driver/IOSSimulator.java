package com.dhemery.victor.driver;


public class IOSSimulator {
	private static final String SHUTDOWN_SCRIPT = "application \"iPhone Simulator\" quit";
	private static final String SIM_LAUNCHER_FILE_NAME = "/Developer/Platforms/iPhoneSimulator.platform/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator";
	public void launch(String appPath) {
		String[] launchCommand = new String[] { SIM_LAUNCHER_FILE_NAME, "-SimulateApplication", appPath };
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

package com.dhemery.victor.driver;


public class IOSSimulator {
	private static final String SHUTDOWN_SCRIPT = "application \"iPhone Simulator\" quit";
	private static final String SIM_LAUNCHER_FILE_NAME = "iphonesim";
	private String simLauncherPath = pathForResource(SIM_LAUNCHER_FILE_NAME);

	public void launch(String appPath) {
		String[] launchCommand = new String[] { simLauncherPath, "launch", appPath };
		run(launchCommand);
	}

	private String pathForResource(String resourceName) {
		return getClass().getResource(resourceName).getPath();
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

package com.dhemery.victor.simulator;


public class WaitUntilSimulatorShutsDownCommand extends AppleScriptCommand { 
	private static final String[] WAIT_FOR_SHUTDOWN_SCRIPT = new String[] {
		"tell application \"System Events\"",
		"set simulatorRunning to (application process \"iPhone Simulator\" exists)", 
		"repeat until simulatorRunning = false", 
		"delay 1", 
		"set simulatorRunning to (application process \"iPhone Simulator\" exists)", 
		"end repeat",
		"end tell", 
	};

	public WaitUntilSimulatorShutsDownCommand() {
		super(WAIT_FOR_SHUTDOWN_SCRIPT);
	}
}

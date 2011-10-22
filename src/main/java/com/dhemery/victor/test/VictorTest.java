package com.dhemery.victor.test;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.dhemery.poller.Poll;
import com.dhemery.victor.driver.SimulatorDriver;
import com.dhemery.victor.frank.FrankDriver;

public class VictorTest {
	private static final String APPLICATION_FILE_PATH = new File("example/Test App Frankified.app/Test App Frankified").getAbsolutePath();
	private static final String SIMULATOR_LAUNCHER_FILE_PATH = "/Developer/Platforms/iPhoneSimulator.platform/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator";
	private static final String SYMBIOTE_SERVER_URL = "http://localhost:37265";

	private static final int TIMEOUT_IN_MILLISECONDS = 20000;
	private static final int POLLING_INTERVAL_IN_MILLISECONDS = 1000;
	private static final Poll poll = new Poll(TIMEOUT_IN_MILLISECONDS, POLLING_INTERVAL_IN_MILLISECONDS);

	private static SimulatorDriver simulator;
	private static FrankDriver frank;

	@BeforeClass
	public static void launchApp() {
		simulator = new SimulatorDriver(SIMULATOR_LAUNCHER_FILE_PATH);
		simulator.launch(APPLICATION_FILE_PATH);
		frank = new FrankDriver(SYMBIOTE_SERVER_URL, poll);
		frank.waitUntilReady();
	}

	@AfterClass
	public static void shutDownSimulator() {
		simulator.shutDown();
	}
	
	public FrankDriver frank() { return frank; }
}

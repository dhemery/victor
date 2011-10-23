package com.dhemery.victor.test;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.dhemery.poller.Poll;
import com.dhemery.victor.elements.ElementFactory;
import com.dhemery.victor.frank.client.FrankClient;
import com.dhemery.victor.frank.elements.FrankElementFactory;
import com.dhemery.victor.frank.phone.FrankSimulatedPhone;
import com.dhemery.victor.phone.Phone;
import com.dhemery.victor.simulator.Simulator;

public class VictorTest {
	private static final String APPLICATION_FILE_PATH = new File("example/Test App Frankified.app/Test App Frankified").getAbsolutePath();
	private static final String SIMULATOR_LAUNCHER_FILE_PATH = "/Developer/Platforms/iPhoneSimulator.platform/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator";
	private static final String FRANK_SERVER_URL = "http://localhost:37265";

	private static final int TIMEOUT_IN_MILLISECONDS = 20000;
	private static final int POLLING_INTERVAL_IN_MILLISECONDS = 1000;
	private static final Poll poll = new Poll(TIMEOUT_IN_MILLISECONDS, POLLING_INTERVAL_IN_MILLISECONDS);

	private static Simulator simulator;
	private static FrankClient frankClient;
	private static ElementFactory elementFactory;
	private static Phone phone;

	@BeforeClass
	public static void launchApp() throws IOException {
		frankClient = new FrankClient(FRANK_SERVER_URL);
		simulator = new Simulator(SIMULATOR_LAUNCHER_FILE_PATH);
		simulator.launch(APPLICATION_FILE_PATH);
		frankClient.waitUntilReady(poll);
		elementFactory = new FrankElementFactory(frankClient, poll);
		phone = new FrankSimulatedPhone(simulator, frankClient);
	}

	@AfterClass
	public static void shutDownSimulator() throws IOException, InterruptedException {
		simulator.shutDown();
	}

	public Phone phone() { return phone; }
	public ElementFactory elementFactory() { return elementFactory; }
}

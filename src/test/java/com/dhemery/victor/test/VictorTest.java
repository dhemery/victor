package com.dhemery.victor.test;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.dhemery.poller.Poll;
import com.dhemery.victor.Application;
import com.dhemery.victor.Phone;
import com.dhemery.victor.frank.FrankApplication;
import com.dhemery.victor.frank.FrankSimulatedPhone;
import com.dhemery.victor.frank.client.FrankClient;
import com.dhemery.victor.simulator.Simulator;
import com.dhemery.victor.test.properties.RequiredProperties;

public class VictorTest {
	private static Application application;
	private static Phone phone;

	@BeforeClass
	public static void launchApp() throws IOException {
		RequiredProperties properties = new RequiredProperties("victor.properties");

		String simulatorPath = properties.get("simulator.path");
		String applicationPath = new File(properties.get("application.path")).getAbsolutePath();
		phone = frankSimulatedPhone(simulatorPath, applicationPath);

		long timeout = Long.parseLong(properties.get("polling.timeout"));
		long pollingInterval = Long.parseLong(properties.get("polling.interval"));
		String frankServerUrl = properties.get("server.url");
		application = frankApplication(timeout, pollingInterval, frankServerUrl);
	}

	private static Application frankApplication(long timeout, long pollingInterval, String frankServerUrl) {
		Poll poll = new Poll(timeout, pollingInterval);
		FrankClient frankClient = new FrankClient(frankServerUrl);
		frankClient.waitUntilReady(poll);
		return new FrankApplication(frankClient, poll);
	}

	private static Phone frankSimulatedPhone(String simulatorPath, String applicationPath) throws IOException {
		Simulator simulator = new Simulator(simulatorPath);
		simulator.launch(applicationPath);
		return new FrankSimulatedPhone(simulator);
	}

	@AfterClass
	public static void shutDownPhone() throws IOException, InterruptedException {
		phone.shutDown();
	}

	public Application application() { return application; }
	public Phone phone() { return phone; }
}

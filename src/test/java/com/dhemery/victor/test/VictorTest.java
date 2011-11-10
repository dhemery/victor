package com.dhemery.victor.test;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.dhemery.poller.Poll;
import com.dhemery.poller.PollTimeoutException;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.Victor;
import com.dhemery.victor.test.properties.RequiredProperties;

public class VictorTest {
	private static ApplicationDriver application;
	private static PhoneDriver phone;

	@BeforeClass
	public static void launchApp() throws IOException, PollTimeoutException {
		RequiredProperties properties = new RequiredProperties("my.properties", "default.properties");

		String simulatorPath = properties.get("simulator.path");
		String applicationPath = new File(properties.get("application.path")).getAbsolutePath();
		String frankServerUrl = properties.get("frank.server.url");

		long timeout = properties.getInteger("polling.timeout");
		long pollingInterval = properties.getInteger("polling.interval");
		Poll poll = new Poll(timeout, pollingInterval);
		
		Victor launcher = new Victor();
		launcher.launch(simulatorPath, applicationPath, frankServerUrl, poll);
		application = launcher.application();
		phone = launcher.phone();
	}

	@AfterClass
	public static void shutDownPhone() throws IOException, InterruptedException {
		phone.shutDown();
	}

	public ApplicationDriver application() { return application; }
	public PhoneDriver phone() { return phone; }
}

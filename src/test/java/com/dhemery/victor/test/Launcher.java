package com.dhemery.victor.test;

import java.io.File;
import java.io.IOException;

import com.dhemery.poller.Poll;
import com.dhemery.poller.PollTimeoutException;
import com.dhemery.properties.RequiredProperties;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.frank.FrankClient;
import com.dhemery.victor.frank.drivers.FrankApplicationDriver;
import com.dhemery.victor.frank.drivers.FrankPhoneDriver;
import com.dhemery.victor.simulator.LocalSimulator;
import com.dhemery.victor.simulator.Simulator;

public class Launcher {
	private final String applicationPath;
	private final String defaultSelectorEngine;
	private final FrankClient frank;
	private final Poll poll;
	private final Simulator simulator;

	public Launcher(RequiredProperties configuration) {
		String simulatorPath = new File(configuration.get("simulator.path")).getAbsolutePath();
		applicationPath = new File(configuration.get("application.path")).getAbsolutePath();
		String frankServerUrl = configuration.get("frank.server.url");
		defaultSelectorEngine = configuration.get("default.selector.engine");
		Integer timeout = configuration.getInteger("polling.timeout");
		Integer pollingInterval = configuration.getInteger("polling.interval");

		poll = new Poll(timeout, pollingInterval);
		frank = new FrankClient(frankServerUrl);
		simulator = new LocalSimulator(simulatorPath);
	}

	public ApplicationDriver application() {
		return new FrankApplicationDriver(frank, defaultSelectorEngine, poll);
	}
	
	public void launch() throws IOException, PollTimeoutException {
		simulator.launch(applicationPath);
		frank.waitForServer(poll);
	}

	public PhoneDriver phone() {
		return new FrankPhoneDriver(simulator, frank);
	}
}

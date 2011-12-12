package com.dhemery.victor.test;

import static com.dhemery.victor.frank.Ready.ready;

import java.io.File;
import java.io.IOException;

import com.dhemery.pollable.PolledSubject;
import com.dhemery.poller.PollTimer;
import com.dhemery.properties.RequiredProperties;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.frank.FrankClient;
import com.dhemery.victor.frank.drivers.FrankApplicationDriver;
import com.dhemery.victor.frank.drivers.FrankPhoneDriver;
import com.dhemery.victor.simulator.AlreadyRunningSimulator;
import com.dhemery.victor.simulator.Simulator;
import com.dhemery.victor.simulator.VictorOwnedSimulator;


public class Launcher {
	private final String applicationPath;
	private final String defaultSelectorEngine;
	private final FrankClient frank;
	private final PollTimer pollTimer;
	private final Simulator simulator;

	public Launcher(RequiredProperties configuration) {
		String simulatorPath = new File(configuration.get("simulator.path")).getAbsolutePath();
		applicationPath = new File(configuration.get("application.path")).getAbsolutePath();
		String frankServerUrl = configuration.get("frank.server.url");
		defaultSelectorEngine = configuration.get("default.selector.engine");
		Integer timeout = configuration.getInteger("polling.timeout");
		Integer pollingInterval = configuration.getInteger("polling.interval");
		Boolean victorOwnsSimulator = Boolean.parseBoolean(configuration.get("victor.owns.simulator"));

		pollTimer = new PollTimer(timeout, pollingInterval);
		frank = new FrankClient(frankServerUrl);
		if(victorOwnsSimulator)
			simulator = new VictorOwnedSimulator(simulatorPath);
		else
			simulator = new AlreadyRunningSimulator();
	}

	public ApplicationDriver application() {
		return new FrankApplicationDriver(frank, defaultSelectorEngine);
	}
	
	public void launch() throws IOException {
		simulator.launch(applicationPath);
		waitUntil(frank).is(ready());
	}

	private PolledSubject<FrankClient> waitUntil(FrankClient frank) {
		return new PolledSubject<FrankClient>(frank, pollTimer);
	}

	public PhoneDriver phone() {
		return new FrankPhoneDriver(simulator, frank);
	}

	public PollTimer pollTimer() {
		return pollTimer;
	}
}

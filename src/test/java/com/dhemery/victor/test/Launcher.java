package com.dhemery.victor.test;

import static com.dhemery.victor.frank.FrankConditions.ready;

import java.io.File;
import java.io.IOException;

import com.dhemery.polling.SystemClockPollTimer;
import com.dhemery.properties.RequiredProperties;
import com.dhemery.sentences.Sentence;
import com.dhemery.sentences.Sentences;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.frank.FrankClient;
import com.dhemery.victor.frank.drivers.FrankApplicationDriver;
import com.dhemery.victor.frank.drivers.FrankPhoneDriver;
import com.dhemery.victor.simulator.AlreadyRunningSimulator;
import com.dhemery.victor.simulator.RemoteSimulator;
import com.dhemery.victor.simulator.Simulator;
import com.dhemery.victor.simulator.VictorOwnedSimulator;


public class Launcher {
	private final FrankClient frank;
	private Simulator simulator;
	private final RequiredProperties configuration;

	public Launcher(RequiredProperties configuration) {
		this.configuration = configuration;
		frank = frankClient();
	}

	private Simulator alreadyRunningSimulator() {
		return new AlreadyRunningSimulator();
	}

	public ApplicationDriver application() {
		String defaultSelectorEngine = configuration.get("default.selector.engine");
		return new FrankApplicationDriver(frank, defaultSelectorEngine);
	}

	public FrankClient frankClient() {
		String frankServerUrl = configuration.get("frank.server.url");
		return new FrankClient(frankServerUrl);
	}

	public void launch() throws IOException {
		String simulatorLocation = configuration.get("simulator.location");
		if(simulatorLocation.equalsIgnoreCase("local")) {
			Boolean launchNew = Boolean.parseBoolean(configuration.get("local.simulator.launch.new"));
			if(launchNew) {
				simulator = launchLocalSimulator();
			} else {
				simulator = alreadyRunningSimulator();
			}
		} else if(simulatorLocation.equalsIgnoreCase("remote")) {
			simulator = launchRemoteSimulator();
		}
		waitUntil(frank).is(ready());
	}

	private Simulator launchLocalSimulator() throws IOException {
		String simulatorPath = new File(configuration.get("local.simulator.path")).getAbsolutePath();
		String applicationPath = new File(configuration.get("local.application.path")).getAbsolutePath();
		return new VictorOwnedSimulator(simulatorPath).launch(applicationPath);
	}

	private Simulator launchRemoteSimulator() throws IOException {
		String simulatorUrl = configuration.get("remote.simulator.url");
		String applicationPath = new File(configuration.get("remote.application.path")).getAbsolutePath();
		return new RemoteSimulator(simulatorUrl).launch(applicationPath);
	}

	public PhoneDriver phone() {
		return new FrankPhoneDriver(simulator, frank);
	}

	public Sentences sentences() {
		Integer timeout = configuration.getInteger("polling.timeout");
		Integer pollingInterval = configuration.getInteger("polling.interval");
		return new Sentences(new SystemClockPollTimer(timeout, pollingInterval));
	}

	private Sentence<FrankClient,Void> waitUntil(FrankClient frank) {
		return sentences().waitUntil(frank);
	}
}

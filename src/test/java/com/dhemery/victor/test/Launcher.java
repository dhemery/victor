package com.dhemery.victor.test;

import static com.dhemery.victor.frank.FrankConditions.ready;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.polling.SystemClockPollTimer;
import com.dhemery.properties.RequiredProperties;
import com.dhemery.sentences.Sentence;
import com.dhemery.sentences.Sentences;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.frank.FrankClient;
import com.dhemery.victor.frank.drivers.FrankApplicationDriver;
import com.dhemery.victor.frank.drivers.FrankPhoneDriver;
import com.dhemery.victor.simulator.Simulator;
import com.dhemery.victor.simulator.local.LocalSimulator;
import com.dhemery.victor.simulator.remote.RemoteSimulator;


public class Launcher {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final FrankClient frank;
	private Simulator simulator;
	private final RequiredProperties configuration;
	private final String simulatorHost;

	public Launcher(RequiredProperties configuration) {
		this.configuration = configuration;
		simulatorHost = configuration.get("simulator.host");
		frank = frankClient();
	}

	public ApplicationDriver application() {
		String defaultSelectorEngine = configuration.get("default.selector.engine");
		return new FrankApplicationDriver(frank, defaultSelectorEngine);
	}

	public FrankClient frankClient() {
		String frankServerUrl = urlForSimulatorHostPort(configuration.get("frank.server.port"));
		return new FrankClient(frankServerUrl);
	}

	public void launch() throws IOException {
		if(simulatorHost.equals("localhost")) {
			simulator = launchLocalSimulator();
		} else {
			simulator = launchRemoteSimulator();
		}
		Boolean launchNew = Boolean.parseBoolean(configuration.get("simulator.launch.new"));
		if(launchNew) {
			String applicationPath = configuration.get("application.path");
			String deviceType = configuration.get("simulator.device.type");
			simulator.launch(applicationPath, deviceType);
		}
		waitUntil(frank).is(ready());
	}

	private Simulator launchLocalSimulator() throws IOException {
		log.debug("Launching local simulator");
		String simulatorPath = new File(configuration.get("simulator.path")).getAbsolutePath();
		return new LocalSimulator(simulatorPath);
	}

	private Simulator launchRemoteSimulator() throws IOException {
		String simulatorUrl = urlForSimulatorHostPort(configuration.get("simulator.server.port"));
		log.debug("Launching simulator on remote server {}", simulatorUrl);
		return new RemoteSimulator(simulatorUrl);
	}

	public PhoneDriver phone() {
		return new FrankPhoneDriver(simulator, frank);
	}

	public Sentences sentences() {
		Integer timeout = configuration.getInteger("polling.timeout");
		Integer pollingInterval = configuration.getInteger("polling.interval");
		return new Sentences(new SystemClockPollTimer(timeout, pollingInterval));
	}

	public String urlForSimulatorHostPort(String simulatorServerPort) {
		return String.format("http://%s:%s", simulatorHost, simulatorServerPort);
	}

	private Sentence<FrankClient,Void> waitUntil(FrankClient frank) {
		return sentences().waitUntil(frank);
	}
}

package com.dhemery.victor.test;

import static com.dhemery.victor.frank.Ready.ready;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.polling.MatcherPoll;
import com.dhemery.polling.PollTimer;
import com.dhemery.polling.SystemClockPollTimer;
import com.dhemery.properties.RequiredProperties;
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
		return new FrankApplicationDriver(frank);
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
			log.debug("Launching simulator");
			simulator.launch(applicationPath);
		}
		new MatcherPoll<FrankClient>(frank, is(ready()), timer()).run();
	}

	private Simulator launchLocalSimulator() throws IOException {
	    String iosVersion = configuration.get("simulator.sdk.version");
		log.debug("Launching local {} simulator", iosVersion);
		return new LocalSimulator(iosVersion, false);
	}

	private Simulator launchRemoteSimulator() throws IOException {
		String simulatorUrl = urlForSimulatorHostPort(configuration.get("simulator.server.port"));
		log.debug("Launching simulator on remote server {}", simulatorUrl);
		return new RemoteSimulator(simulatorUrl);
	}

	public PhoneDriver phone() {
		return new FrankPhoneDriver(simulator);
	}

	public PollTimer timer() {
		Integer timeout = configuration.getInteger("polling.timeout");
		Integer pollingInterval = configuration.getInteger("polling.interval");
		return new SystemClockPollTimer(timeout, pollingInterval);
	}

	public String urlForSimulatorHostPort(String simulatorServerPort) {
		return String.format("http://%s:%s", simulatorHost, simulatorServerPort);
	}

	public Simulator simulator() {
		return simulator;
	}
}

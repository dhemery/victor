package com.dhemery.victor;

import java.io.File;
import java.io.IOException;

import com.dhemery.poller.Poll;
import com.dhemery.poller.PollTimeoutException;
import com.dhemery.properties.RequiredProperties;
import com.dhemery.victor.application.server.AcknowledgesPing;
import com.dhemery.victor.application.server.ApplicationServer;
import com.dhemery.victor.frank.FrankServer;
import com.dhemery.victor.remote.RemoteApplicationDriver;
import com.dhemery.victor.remote.RemotePhoneDriver;
import com.dhemery.victor.simulator.LocalSimulator;
import com.dhemery.victor.simulator.Simulator;

/**
 * <p>Runs a Frankified iOS application in an iOS Simulator.</p>
 * 
 * <p>
 * Creates a {@link PhoneDriver} that can interact with the simulated iOS device in the sumulator.
 * Creates an {@link ApplicationDriver} that can interact with the application running in the simulated phone.
 * </p>
 *
 * <p><strong>Prerequisite: A "Frankified" application.</strong>
 * {@code Victor} communicates to the application through a Frank server embedded into the application.
 * To use Victor, you must create a "Frankified" version of your application.
 * The process is described at <a href="https://github.com/moredip/Frank/wiki/Getting-started-with-Frank">the Frank site</a>.
 * The video demonstrations there are particularly helpful.
 * </p>
 * 
 * <p><strong>Prerequisite: Accessibility enabled in the simulator.</strong>
 * The Frank server embedded into the application communicates with the application
 * through the (simulated) device's accessibility feature.
 * For this to work, you must enable accessibility on the simulated device.
 * </p>
 * <p>
 * In a running Simulator, open the Settings application.
 * Go to General settings, then Accessibility.
 * Turn accessibility on.
 * This setting persists across simulator sessions.
 * </p>
 * 
 * <p><strong>Prerequisite: Assistive device access enabled on the computer.</strong>
 * Victor communicates with the simulated phone by executing AppleScript commands
 * that select menus in the iOS Simulator application.
 * For this to work, you must enable "assistive device" access on your computer.
 * </p>
 * <p>
 * Open your computer's "Universal Access" preferences.
 * Check the checkbox for "Enable access for assistive devices."
 * </p>
 * @author Dale Emery
 *
 */
public class Victor {
	private ApplicationDriver application;
	private PhoneDriver phone;
	private final String simulatorPath;
	private final String applicationPath;
	private final String frankServerUrl;
	private final Poll poll;

	/** 
	 * <p>The configuration must define the following properties:</p>
	 * <ul>
	 * <li>{@code simulator.path}: The file path to the iOS Simulator executable.</li>
	 * <li>{@code application.path}: The file path to the application to launch in the simulator.</li>
	 * <li>{@code frank.server.url}: The URL where the embedded Frank server listens.</li>
	 * <li>{@code polling.timeout}: How long to persist polling (for {@code eventually()} methods) before timing out.</li>
	 * <li>{@code polling.interval}: How long to wait between polls.</li>
	 * </ul>
	 * 
	 * @param configuration dictionary of configuration properties.
	 */
	public Victor(RequiredProperties configuration) {
		this(configuration.get("application.path"),
				configuration.get("simulator.path"),
				configuration.get("frank.server.url"),
				configuration.getInteger("polling.timeout"),
				configuration.getInteger("polling.interval"));
	}

	public Victor(String applicationPath, String simulatorPath, String frankServerUrl, Integer timeout, Integer pollingInterval) {
		this.applicationPath = new File(applicationPath).getAbsolutePath();
		this.simulatorPath = new File(simulatorPath).getAbsolutePath();
		this.frankServerUrl = frankServerUrl;
		this.poll = new Poll(timeout, pollingInterval);
	}

	/**
	 * @return a driver that can interact with the application launched by the previous call to {@link #launch}. 
	 */
	public ApplicationDriver application() {
		return application;
	}

	public Victor launch() throws IOException, PollTimeoutException {		
		Simulator simulator = new LocalSimulator(simulatorPath);
		ApplicationServer applicationServer = new FrankServer(frankServerUrl);
		simulator.launch(applicationPath);
		poll.until(new AcknowledgesPing(applicationServer));
		phone = new RemotePhoneDriver(simulator, applicationServer);
		application = new RemoteApplicationDriver(applicationServer, poll);
		return this;
	}

	/**
	 * @return a driver that can interact with the phone launched by the previous call to {@link #launch}. 
	 */
	public PhoneDriver phone() {
		return phone;
	}
}

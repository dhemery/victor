package com.dhemery.victor;

import java.io.IOException;

import com.dhemery.poller.Poll;
import com.dhemery.victor.frank.FrankApplication;
import com.dhemery.victor.frank.FrankClient;
import com.dhemery.victor.phone.SimulatedPhone;
import com.dhemery.victor.phone.Simulator;

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

	/**
	 * @return a driver that can interact with the application launched by the previous call to {@link #launch}. 
	 */
	public ApplicationDriver application() {
		return application;
	}

	/**
	 * @param simulatorPath the file path to the iOS Simulator application to use to run the iOS application.
	 * @param iosApplicationPath
	 * @param frankServerUrl
	 * @param poll
	 * @throws IOException
	 */
	public void launch(String simulatorPath, String applicationPath, String frankServerUrl, Poll poll) throws IOException {
		Simulator simulator = new Simulator(simulatorPath);
		simulator.launch(applicationPath);
		phone = new SimulatedPhone(simulator);
		FrankClient frankClient = new FrankClient(frankServerUrl, poll);
		frankClient.waitUntilReady();
		application = new FrankApplication(frankClient, poll);
	}

	/**
	 * @return a driver that can interact with the phone launched by the previous call to {@link #launch}. 
	 */
	public PhoneDriver phone() {
		return phone;
	}
}

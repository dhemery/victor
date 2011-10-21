package com.dhemery.victor.test;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dhemery.poller.Poll;
import com.dhemery.victor.driver.SimulatorDriver;
import com.dhemery.victor.elements.ios.IOSButton;
import com.dhemery.victor.elements.ios.IOSLabel;
import com.dhemery.victor.elements.ios.IOSView;
import com.dhemery.victor.fixtures.DetailDisplay;
import com.dhemery.victor.fixtures.ExampleApplication;
import com.dhemery.victor.fixtures.MasterDisplay;

public class TestOne {
	private static final String APPLICATION_FILE_PATH = new File("example/Test App Frankified.app/Test App Frankified").getAbsolutePath();
	private static final String SIMULATOR_LAUNCHER_FILE_PATH = "/Developer/Platforms/iPhoneSimulator.platform/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator";
	private static final String SYMBIOTE_SERVER_URL = "http://localhost:37265";

	private static final int TIMEOUT_IN_MILLISECONDS = 20000;
	private static final int POLLING_INTERVAL_IN_MILLISECONDS = 1000;

	private static SimulatorDriver simulator = new SimulatorDriver(SIMULATOR_LAUNCHER_FILE_PATH);
	private static ExampleApplication application;

	@BeforeClass
	public static void launchApp() {
		simulator.launch(APPLICATION_FILE_PATH);
		application = new ExampleApplication(SYMBIOTE_SERVER_URL, new Poll(TIMEOUT_IN_MILLISECONDS, POLLING_INTERVAL_IN_MILLISECONDS));
	}

	@AfterClass
	public static void shutDownSimulator() {
		simulator.shutDown();
	}

	@Test
	public void testOne() throws InterruptedException {
		MasterDisplay master = application.masterDisplay();
		DetailDisplay detail = application.detailDisplay();
		IOSView masterView = master.masterView();
		IOSLabel detailLabel = master.detailLabel();
		IOSView detailView = detail.detailView();
		IOSButton masterButton = detail.masterButton();

		masterView.verify().eventually().isPresent();
		masterView.verify().eventually().isVisible();
		detailLabel.verify().eventually().isPresent();
		detailLabel.verify().eventually().isVisible();
//		detailView.verify().isNotPresent();
//		detailView.verify().isNotVisible();
//		masterButton.verify().isNotPresent();
//		masterButton.verify().isNotVisible();

		detailLabel.whenPresent().touch();

		detailView.verify().eventually().isPresent();
		detailView.verify().eventually().isVisible();
		masterButton.verify().eventually().isPresent();
		masterButton.verify().eventually().isVisible();
//		masterView.verify().isNotPresent();
//		masterView.verify().isNotVisible();
//		detailLabel.verify().isNotPresent();
//		detailLabel.verify().isNotVisible();

		masterButton.whenPresent().touch();

		masterView.verify().eventually().isPresent();
		masterView.verify().eventually().isVisible();
		detailLabel.verify().eventually().isPresent();
		detailLabel.verify().eventually().isVisible();
//		detailView.verify().isNotPresent();
//		detailView.verify().isNotVisible();
//		masterButton.verify().isNotPresent();
//		masterButton.verify().isNotVisible();
	}
}

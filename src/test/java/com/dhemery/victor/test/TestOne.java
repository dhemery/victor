package com.dhemery.victor.test;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dhemery.victor.driver.IOSSimulatorDriver;
import com.dhemery.victor.elements.ios.IOSButton;
import com.dhemery.victor.elements.ios.IOSLabel;
import com.dhemery.victor.elements.ios.IOSView;
import com.dhemery.victor.fixtures.DetailDisplay;
import com.dhemery.victor.fixtures.ExampleApplication;
import com.dhemery.victor.fixtures.MasterDisplay;

public class TestOne {
	public static final String PATH = new File("example/Test App Frankified.app/Test App Frankified").getAbsolutePath();

	private static IOSSimulatorDriver simulator = new IOSSimulatorDriver();
	private static ExampleApplication app;

	@BeforeClass
	public static void launchApp() {
		simulator.launch(PATH);
		app = new ExampleApplication();
	}

	@AfterClass
	public static void shutDownSimulator() {
		simulator.shutDown();
	}

	@Test
	public void testOne() throws InterruptedException {
		MasterDisplay master = app.masterDisplay();
		DetailDisplay detail = app.detailDisplay();
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

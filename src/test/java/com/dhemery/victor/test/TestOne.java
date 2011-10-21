package com.dhemery.victor.test;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dhemery.victor.driver.IOSApplicationDriver;
import com.dhemery.victor.driver.IOSSimulatorDriver;
import com.dhemery.victor.driver.SymbioteClient;
import com.dhemery.victor.elements.ios.IOSButton;
import com.dhemery.victor.elements.ios.IOSLabel;
import com.dhemery.victor.elements.ios.IOSView;
import com.dhemery.victor.fixtures.DetailDisplay;
import com.dhemery.victor.fixtures.MasterDisplay;

public class TestOne {
	public static final String PATH = new File("example/Test App Frankified.app/Test App Frankified").getAbsolutePath();

	private static IOSSimulatorDriver simulator = new IOSSimulatorDriver();
	private static IOSApplicationDriver app = new SymbioteClient();

	@BeforeClass
	public static void launchApp() {
		simulator.launch(PATH);
		app.waitUntilReady();
	}

	@AfterClass
	public static void shutDownSimulator() {
		simulator.shutDown();
	}

	@Test
	public void testOne() throws InterruptedException {
		MasterDisplay master = new MasterDisplay(app);
		DetailDisplay detail = new DetailDisplay(app);
		IOSView masterView = master.masterView();
		IOSLabel detailLabel = master.detailLabel();
		IOSView detailView = detail.detailView();
		IOSButton masterButton = detail.masterButton();

		masterView.verify().isPresent();
		masterView.verify().isVisible();
//		masterView.verify().isNotPresent();
//		masterView.verify().isNotVisible();
		detailLabel.verify().isPresent();
		detailLabel.verify().isVisible();

		detailLabel.whenPresent().touch();

		detailView.verify().isPresent();
		detailView.verify().isVisible();
		masterButton.verify().isPresent();
		masterButton.verify().isVisible();

		masterButton.whenPresent().touch();

		masterView.verify().eventually().isPresent();
		masterView.verify().eventually().isVisible();
		detailLabel.verify().eventually().isPresent();
		detailLabel.verify().eventually().isVisible();
		
		detailLabel.whenPresent().touch();
	}
}

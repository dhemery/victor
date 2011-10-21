package com.dhemery.victor.test;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dhemery.victor.driver.IOSApplicationDriver;
import com.dhemery.victor.driver.IOSSimulator;
import com.dhemery.victor.driver.VictorClient;
import com.dhemery.victor.elements.IOSButton;
import com.dhemery.victor.elements.IOSLabel;
import com.dhemery.victor.elements.IOSView;
import com.dhemery.victor.fixtures.DetailDisplay;
import com.dhemery.victor.fixtures.MasterDisplay;

public class TestOne {
	public static final String PATH = new File("example/Test App Frankified.app/Test App Frankified").getAbsolutePath();

	private static IOSSimulator simulator = new IOSSimulator();
	private static IOSApplicationDriver app = new VictorClient();

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

		detailLabel.touch();

		detailView.verify().isPresent();
		detailView.verify().isVisible();
		masterButton.verify().isPresent();
		masterButton.verify().isVisible();

		masterButton.touch();

		masterView.verify().isPresent();
		masterView.verify().isVisible();
		detailLabel.verify().isPresent();
		detailLabel.verify().isVisible();
	}
}

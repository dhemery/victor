package com.dhemery.victor.test;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dhemery.poller.For;
import com.dhemery.poller.TimeoutException;
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
		sleep(5000);

		MasterDisplay master = new MasterDisplay(app);
		IOSView masterView = master.masterView();
		IOSLabel detailLabel = master.detailLabel();

		assertTrue(masterView.exists());
		assertTrue(masterView.isVisible());
		assertTrue(detailLabel.exists());
		assertTrue(detailLabel.isVisible());

		detailLabel.touch();
		sleep(5000);

		DetailDisplay detail = new DetailDisplay(app);
		IOSView detailView = detail.detailView();
		IOSButton masterButton = detail.masterButton();

		assertTrue(detailView.exists());
		assertTrue(detailView.isVisible());
		assertTrue(masterButton.exists());
		assertTrue(masterButton.isVisible());

		masterButton.touch();
		sleep(5000);

		assertTrue(masterView.exists());
		assertTrue(masterView.isVisible());
		assertTrue(detailLabel.exists());
		assertTrue(detailLabel.isVisible());
	}

	private void sleep(int i) {
		try {
			new For(i).poll().untilTimerExpires();
		} catch(TimeoutException e) {
		}
	}
}

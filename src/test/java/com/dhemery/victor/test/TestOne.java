package com.dhemery.victor.test;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dhemery.victor.driver.IOSApplicationDriver;
import com.dhemery.victor.driver.IOSSimulator;
import com.dhemery.victor.driver.VictorClient;
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
		assertTrue(master.detailLabel().isVisible());
	}
}

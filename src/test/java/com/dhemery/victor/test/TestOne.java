package com.dhemery.victor.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dhemery.victor.driver.IOSSimulator;
import com.dhemery.victor.driver.VictorClient;
import com.dhemery.victor.fixtures.MasterDisplay;
import com.dhemery.victor.fixtures.MyApplication;

public class TestOne {
	private static IOSSimulator simulator = new IOSSimulator();
	private static VictorClient app = new MyApplication();

	@BeforeClass
	public static void launchApp() {
		simulator.launch(app.path());
		app.waitForServer();
	}

	@AfterClass
	public static void shutDownSimulator() {
		simulator.shutDown();
	}

	@Test
	public void testOne() throws InterruptedException {
		MasterDisplay master = new MasterDisplay(app);
		assertTrue(master.detailsLabel().isVisible());
	}
}

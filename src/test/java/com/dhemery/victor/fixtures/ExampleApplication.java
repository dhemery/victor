package com.dhemery.victor.fixtures;

import com.dhemery.poller.Poll;
import com.dhemery.victor.frank.FrankDriver;


public class ExampleApplication {
	private final FrankDriver driver;
	
	public ExampleApplication(String serverUrl, Poll poll) {
		driver = new FrankDriver(serverUrl, poll);
		driver.waitUntilReady();
	}
	
	public DetailDisplay detailDisplay() {
		return new DetailDisplay(driver);
	}

	public MasterDisplay masterDisplay() {
		return new MasterDisplay(driver);
	}
}

package com.dhemery.victor.fixtures;

import com.dhemery.poller.Poll;
import com.dhemery.victor.symbiote.SymbioteDriver;


public class ExampleApplication {
	private final SymbioteDriver driver;
	
	public ExampleApplication(String serverUrl, Poll poll) {
		driver = new SymbioteDriver(serverUrl, poll);
		driver.waitUntilReady();
	}
	
	public DetailDisplay detailDisplay() {
		return new DetailDisplay(driver);
	}

	public MasterDisplay masterDisplay() {
		return new MasterDisplay(driver);
	}
}

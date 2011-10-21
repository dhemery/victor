package com.dhemery.victor.fixtures;

import com.dhemery.victor.driver.Poll;
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

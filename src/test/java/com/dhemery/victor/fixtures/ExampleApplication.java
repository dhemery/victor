package com.dhemery.victor.fixtures;

import com.dhemery.victor.driver.IOSApplicationDriver;
import com.dhemery.victor.driver.SymbioteClient;


public class ExampleApplication {
	private final IOSApplicationDriver driver = new SymbioteClient();
	
	public ExampleApplication() {
		driver.waitUntilReady();
	}
	
	public DetailDisplay detailDisplay() {
		return new DetailDisplay(driver);
	}

	public MasterDisplay masterDisplay() {
		return new MasterDisplay(driver);
	}
}

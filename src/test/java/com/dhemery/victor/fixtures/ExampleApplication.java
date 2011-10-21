package com.dhemery.victor.fixtures;

import com.dhemery.victor.driver.SymbioteApplicationDriver;


public class ExampleApplication {
	private final SymbioteApplicationDriver driver = new SymbioteApplicationDriver();
	
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

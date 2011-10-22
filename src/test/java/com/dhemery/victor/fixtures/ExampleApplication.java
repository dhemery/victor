package com.dhemery.victor.fixtures;

import com.dhemery.victor.frank.FrankDriver;

public class ExampleApplication {
	private final FrankDriver driver;
	
	public ExampleApplication(FrankDriver driver) {
		this.driver = driver;
	}
	
	public DetailDisplay detailDisplay() {
		return new DetailDisplay(driver);
	}

	public MasterDisplay masterDisplay() {
		return new MasterDisplay(driver);
	}
}

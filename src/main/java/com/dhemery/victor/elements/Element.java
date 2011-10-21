package com.dhemery.victor.elements;

import com.dhemery.victor.driver.ApplicationDriver;

public interface Element extends ElementCommands, ElementConditions {
	public ApplicationDriver driver();
	public String locator();
}

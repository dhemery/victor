package com.dhemery.victor.elements;

import com.dhemery.victor.driver.ApplicationDriver;

public interface Element extends ElementCommands, ElementConditions {
	public ApplicationDriver driver();
	public Locator locator();
	public ElementAssertion verify();
	public ElementCommands whenPresent();
	public ElementCommands whenVisible();
}

package com.dhemery.victor.elements;

public interface Element extends ElementCommands, ElementConditions {
	public Locator locator();
	public ElementAssertion verify();
	public ElementCommands whenPresent();
	public ElementCommands whenVisible();
}

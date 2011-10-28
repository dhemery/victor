package com.dhemery.victor;

import com.dhemery.victor.elements.ElementAssertion;
import com.dhemery.victor.elements.ElementCommands;
import com.dhemery.victor.elements.ElementConditions;

public interface Element extends ElementCommands, ElementConditions {
	public String query();
	public ElementAssertion verify();
	public ElementCommands whenPresent();
	public ElementCommands whenVisible();
}

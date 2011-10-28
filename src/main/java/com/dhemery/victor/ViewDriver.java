package com.dhemery.victor;

import com.dhemery.poller.Condition;
import com.dhemery.victor.views.ElementAssertion;
import com.dhemery.victor.views.ElementCommands;
import com.dhemery.victor.views.ElementConditions;

/**
 * A driver that can interact with one or more views in an iOS application.
 * @author Dale Emery
 *
 */
public interface ViewDriver extends ElementCommands, ElementConditions {
	
	/**
	 * @return the query used to identify the views represented by this driver.
	 */
	public String query();

	/**
	 * @return a view driver that can verify whether the view satisfies certain conditions.
	 */
	public ElementAssertion verify();
	
	/**
	 * 
	 * @param condition a condition that must be satisfied before the driver executes each command.
	 * @return a view driver that waits for the condition to be satisfied before it executes each command.
	 */
	public ElementCommands when(Condition condition);
	
	/**
	 * @return a view driver that waits for the view
	 * to become present before it executes each command.
	 */
	public ElementCommands whenPresent();
	
	/**
	 * @return a view driver that waits for the view
	 * to become visible before it executes each command.
	 */
	public ElementCommands whenVisible();
}

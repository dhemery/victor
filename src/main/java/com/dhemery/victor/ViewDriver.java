package com.dhemery.victor;

import java.io.IOException;

import com.dhemery.poller.Condition;
import com.dhemery.victor.views.ViewAssertion;
import com.dhemery.victor.views.PolledViewCommands;

/**
 * A driver that can interact with one or more views in an iOS application.
 * @author Dale Emery
 */
public interface ViewDriver {
	
	/**
	 * Causes the view to flash visually.
	 * @throws IOException
	 */
	public void flash() throws IOException;

	/**
	 * @return {@code true} if the view is not present, otherwise {@code false}.
	 */
	public boolean isNotPresent();
	
	/**
	 * @return {@code true} if the view is not visible, otherwise {@code false}.
	 */
	public boolean isNotVisible();
	
	/**
	 * @return {@code true} if the view is present, otherwise {@code false}.
	 */
	public boolean isPresent();
	
	/**
	 * @return {@code true} if the view is visible, otherwise {@code false}.
	 */
	public boolean isVisible();

	/**
	 * @return the query used to identify the views represented by this driver.
	 */
	public String query();
	
	/**
	 * Touch the element.
	 * @throws IOException
	 */
	public void touch() throws IOException;
	
	/**
	 * @return a view driver that can verify whether the view satisfies certain conditions.
	 */
	public ViewAssertion verify();
	/**
	 * 
	 * @param condition a condition that must be satisfied before the driver executes each command.
	 * @return a view driver that waits for the condition to be satisfied before it executes each command.
	 */
	public PolledViewCommands when(Condition condition);
	/**
	 * @return a view driver that waits for the view
	 * to become present before it executes each command.
	 */
	public PolledViewCommands whenPresent();
	/**
	 * @return a view driver that waits for the view
	 * to become visible before it executes each command.
	 */
	public PolledViewCommands whenVisible();
}

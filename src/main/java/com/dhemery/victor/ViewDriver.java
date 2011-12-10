package com.dhemery.victor;

import java.io.IOException;

import org.hamcrest.SelfDescribing;

import com.dhemery.poller.Condition;
import com.dhemery.poller.PollTimeoutException;
import com.dhemery.victor.view.ViewAssertion;

/**
 * A driver that can interact with one or more views in an iOS application.
 * @author Dale Emery
 */
public interface ViewDriver extends SelfDescribing {
	
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
	 * @return the query that identifies the views represented by this driver.
	 */
	public Query query();

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
	 * Polls until the specified condition is satisfied.
	 * @param condition
	 * @return this view driver.
	 * @throws PollTimeoutException if the poll times out before the condition is satisfied.
	 */
	public ViewDriver when(Condition condition) throws PollTimeoutException;

	/**
	 * Polls until the view is present.
	 * @return this view driver
	 * @throws PollTimeoutException if the poll times out before the view is present.
	 */
	public ViewDriver whenPresent() throws PollTimeoutException;

	/**
	 * Polls until the view is visible.
	 * @return this view driver
	 * @throws PollTimeoutException if the poll times out before the view is visible.
	 */
	public ViewDriver whenVisible() throws PollTimeoutException;
}

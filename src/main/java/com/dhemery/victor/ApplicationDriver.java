package com.dhemery.victor;

import com.dhemery.poller.PollTimeoutException;
import com.dhemery.victor.application.ApplicationAssertion;

/**
 * A driver that can interact with an application running on an iOS device.
 * @author Dale Emery
 *
 */
public interface ApplicationDriver {
	public enum Orientation {
		LANDSCAPE,
		PORTRAIT,
		UNKNOWN,
	}

	/**
	 * @return {@code true} if the application has the given orientation, otherwise {@code false}.
	 */
	public boolean hasOrientation(Orientation orientation);

	/**
	 * @return the application's current orientation.
	 */
	public Orientation orientation();
	
	/**
	 * @return a verifier that can verify whether the application satisfies certain conditions.
	 */
	public ApplicationAssertion verify();
	
	/**
	 * Create a driver that uses the default selector engine to identify the views it represents.
	 * 
	 * @param selector a selector that describes a set of views to the default selector engine. 
	 * @return a driver that can interact with the views described by the selector.
	 */
	public ViewDriver view(String selector);

	/**
	 * @param selectorEngine the selector engine to use to find the views represented by this driver.
	 * @param selector a selector that describes a set of views to the selector engine.
	 * @return a driver that can interact with views described by the selector.
	 */
	public ViewDriver view(String selectorEngine, String selector);

	/**
	 * Polls until the application server is ready to respond to requests.
	 * @throws PollTimeoutException
	 */
	public void waitUntilReady() throws PollTimeoutException;
}

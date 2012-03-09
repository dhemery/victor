package com.dhemery.victor;

import org.hamcrest.SelfDescribing;


/**
 * A driver that can interact with an application running on an iOS device.
 * @author Dale Emery
 *
 */
public interface ApplicationDriver extends SelfDescribing {
	public enum Orientation {
		LANDSCAPE,
		PORTRAIT,
		UNKNOWN,
	}

	/**
	 * @return the application's current orientation.
	 */
	public Orientation orientation();

	/**
	 * @param selector the view query that identifies the views represented by this driver.
	 * @return a driver that can interact with the views described by the selector.
	 */
	public ViewDriver view(ViewSelector selector);
}

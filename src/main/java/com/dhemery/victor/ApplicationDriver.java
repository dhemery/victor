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
}

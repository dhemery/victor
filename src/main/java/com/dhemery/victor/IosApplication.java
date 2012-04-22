package com.dhemery.victor;

import org.hamcrest.SelfDescribing;


/**
 * Represents an application running on an iOS device.
 * @author Dale Emery
 *
 */
public interface IosApplication extends SelfDescribing {
	public enum Orientation {
		LANDSCAPE,
		PORTRAIT,
		UNKNOWN,
	}

	/**
	 * @return the application's current orientation.
	 */
	public Orientation orientation();
}

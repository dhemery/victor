package com.dhemery.victor;

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
	 * @return a driver that can interact with the views that match the Igor query.
	 */
	public ViewDriver view(String igorQuery);
}

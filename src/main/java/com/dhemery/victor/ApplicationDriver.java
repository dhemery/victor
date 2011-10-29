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
	 * @return a driver that can interact with the views that match the query.
	 */
	public ViewDriver view(String query);

	/**
	 * <p>
	 * Creates a driver that can interact with views that have the given "class" and accessibilityLabel.
	 * </p>
	 * <p>
	 * The {@code type} parameter is similar to the name of an Objective C class of the views to match.
	 * But the {@code type} parameter omits the leading "UI" prefix.
	 * Also, the {@code type} parameter may begin with either an upper or lower case letter.
	 * Except for the first letter, the rest of the type must match the case of the view's Objective C class name.
	 * </p>
	 *  
	 * @param type the "class" of the views to be represented by this driver (see above).
	 * @param accessibilityLabel the accessibilityLabel to be represented by this driver.
	 * @return a driver for the views that have the given type and accessibilityLabel.
	 */
	public ViewDriver view(String type, String accessibilityLabel);
}

package com.dhemery.victor.frank;

/**
 * A response from a Frank {@link OrientationRequest}.
 * @author Dale Emery
 *
 */
public class OrientationResponse {
	private final String orientation;
	
	public OrientationResponse(String orientation) {
		this.orientation = orientation;
	}
	
	/**
	 * @return the application's orientation, as returned from a Frank {@code OrientationRequest}.
	 */
	public String orientation() { return orientation; }
}

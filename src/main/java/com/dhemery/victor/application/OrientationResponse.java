package com.dhemery.victor.application;

/**
 * A response to an orientation() request.
 * @author Dale Emery
 *
 */
public class OrientationResponse {
	public final String orientation;
	
	public OrientationResponse(String orientation) {
		this.orientation = orientation;
	}

	@Override
	public String toString() {
		return orientation;
	}
}
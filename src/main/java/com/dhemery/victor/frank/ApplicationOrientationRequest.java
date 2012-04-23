package com.dhemery.victor.frank;

import com.dhemery.victor.http.HttpRequest;



/**
 * Requests the orientation of an iOS application.
 * @author Dale Emery
 *
 */
public class ApplicationOrientationRequest extends HttpRequest {
	public ApplicationOrientationRequest() {
		super("orientation");
	}
}

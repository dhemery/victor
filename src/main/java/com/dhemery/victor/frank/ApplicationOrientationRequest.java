package com.dhemery.victor.frank;

import com.dhemery.victor.http.HttpRequest;



/**
 * A request to learn the orientation of an application running with a Frank server. 
 * @author Dale Emery
 *
 */
public class ApplicationOrientationRequest extends HttpRequest {
	public ApplicationOrientationRequest() {
		super("orientation");
	}
}

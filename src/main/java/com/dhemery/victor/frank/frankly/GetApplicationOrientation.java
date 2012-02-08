package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.http.HttpRequest;



/**
 * A request to learn the orientation of an application running with a Frank server. 
 * @author Dale Emery
 *
 */
public class GetApplicationOrientation extends HttpRequest {
	public GetApplicationOrientation() {
		super("orientation");
	}
}

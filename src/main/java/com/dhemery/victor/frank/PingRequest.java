package com.dhemery.victor.frank;

import com.dhemery.victor.http.HttpRequest;



/**
 * An request with no "verb."
 * Acts as a "ping" request.
 * @author Dale Emery
 */
public class PingRequest extends HttpRequest {
	public PingRequest() {
		super("");
	}
}

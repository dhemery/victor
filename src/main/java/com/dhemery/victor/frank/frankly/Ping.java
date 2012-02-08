package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.http.HttpRequest;



/**
 * An empty Frank request with no "verb."
 * Acts as a "ping" request.
 * @author Dale Emery
 *
 */
public class Ping extends HttpRequest {
	public Ping() {
		super("");
	}
}

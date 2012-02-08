package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.http.HttpRequest;



/**
 * A request to learn whether accessibility is enabled in the application.
 * @author Dale Emery
 *
 */
public class CheckAccessibility extends HttpRequest {
	public CheckAccessibility() {
		super("accessibility_check");
	}
}

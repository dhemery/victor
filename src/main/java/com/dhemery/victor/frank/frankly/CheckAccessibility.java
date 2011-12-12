package com.dhemery.victor.frank.frankly;



/**
 * A request to learn whether accessibility is enabled in the application.
 * @author Dale Emery
 *
 */
public class CheckAccessibility extends FranklyRequest {
	public CheckAccessibility() {
		super("accessibility_check");
	}
}

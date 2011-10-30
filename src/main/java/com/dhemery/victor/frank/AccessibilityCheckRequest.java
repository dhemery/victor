package com.dhemery.victor.frank;


/**
 * A request to learn whether accessibility is enabled in the application.
 * @author Dale Emery
 *
 */
public class AccessibilityCheckRequest extends Request {
	public AccessibilityCheckRequest() {
		super("accessibility_check");
	}
}

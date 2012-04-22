package com.dhemery.victor.frank;

/**
 * A response to a Frank server accessibility check request. 
 * @author Dale Emery
 */
public class AccessibilityCheckResponse {
	public String accessibility_enabled;

	@Override
	public String toString() {
		return String.format("Enabled: %s", accessibility_enabled);
	}
}

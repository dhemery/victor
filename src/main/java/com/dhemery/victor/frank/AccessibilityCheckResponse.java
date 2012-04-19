package com.dhemery.victor.frank;

/**
 * A response to a Frank server accessibility check request. 
 * @author Dale Emery
 */
public class AccessibilityCheckResponse {
	private String accessibility_enabled;

	public String accessibilityEnabled() { return accessibility_enabled; }

	@Override
	public String toString() {
		return String.format("Enabled: %s", accessibilityEnabled());
	}
}

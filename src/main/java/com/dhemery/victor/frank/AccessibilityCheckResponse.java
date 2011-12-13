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
		StringBuilder builder = new StringBuilder();
		builder.append("[accessibilityCheckResponse:");
		builder.append(String.format("[accessibility_enabled:%s]", accessibilityEnabled()));
		builder.append("]");
		return builder.toString();
	}
}

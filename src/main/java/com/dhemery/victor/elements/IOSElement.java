package com.dhemery.victor.elements;

import com.dhemery.victor.driver.IOSApplicationDriver;

public class IOSElement {
	private final String locator;
	private final IOSApplicationDriver app;

	public IOSElement(IOSApplicationDriver app, String locator) {
		this.app = app;
		this.locator = locator;
	}

	public boolean isVisible() {
		return app.elementExists(locator) && app.elementIsVisible(locator);
	}
}

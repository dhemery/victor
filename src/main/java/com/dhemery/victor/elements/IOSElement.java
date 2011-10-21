package com.dhemery.victor.elements;

import com.dhemery.victor.driver.IOSApplicationDriver;

public class IOSElement implements IOSElementCommands, IOSElementConditions {
	private final String locator;
	private final IOSApplicationDriver app;

	public IOSElement(IOSApplicationDriver app, String locator) {
		this.app = app;
		this.locator = locator;
	}

	public IOSElementAssertion verify() {
		return new IOSElementAssertion(this);
	}

	@Override
	public boolean isPresent() {
		return app.elementExists(this);
	}

	@Override
	public boolean isVisible() {
		return app.elementIsVisible(this);
	}

	@Override
	public void touch() {
		app.touch(this);
	}

	@Override
	public String locator() {
		return locator;
	}

	@Override
	public boolean isNotPresent() {
		return !isPresent();
	}

	@Override
	public boolean isNotVisible() {
		return !isVisible();
	}
}

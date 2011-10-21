package com.dhemery.victor.elements.ios;

import com.dhemery.victor.driver.IOSApplicationDriver;
import com.dhemery.victor.elements.Element;
import com.dhemery.victor.elements.ElementAssertion;
import com.dhemery.victor.elements.ElementCommands;
import com.dhemery.victor.elements.PolledElementCommands;
import com.dhemery.victor.elements.conditions.IsPresent;
import com.dhemery.victor.elements.conditions.IsVisible;

public class IOSElement implements Element {
	private final String locator;
	private final IOSApplicationDriver app;

	public IOSElement(IOSApplicationDriver app, String locator) {
		this.app = app;
		this.locator = locator;
	}

	@Override
	public boolean isPresent() {
		return app.isPresent(this);
	}

	@Override
	public boolean isVisible() {
		return app.isVisible(this);
	}

	@Override
	public boolean isNotPresent() {
		return !isPresent();
	}

	@Override
	public boolean isNotVisible() {
		return !isVisible();
	}

	@Override
	public void touch() {
		app.touch(this);
	}

	public String locator() {
		return locator;
	}

	public ElementCommands whenPresent() {
		return new PolledElementCommands(this, new IsPresent(this));
	}

	public ElementCommands whenVisible() {
		return new PolledElementCommands(this, new IsVisible(this));
	}
	
	public ElementAssertion verify() {
		return new ElementAssertion(this);
	}
}

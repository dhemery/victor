package com.dhemery.victor.elements.ios;

import com.dhemery.victor.driver.ApplicationDriver;
import com.dhemery.victor.elements.Element;
import com.dhemery.victor.elements.ElementAssertion;
import com.dhemery.victor.elements.ElementCommands;
import com.dhemery.victor.elements.Locator;
import com.dhemery.victor.elements.PolledElementCommands;
import com.dhemery.victor.elements.conditions.IsPresent;
import com.dhemery.victor.elements.conditions.IsVisible;

public class IOSElement implements Element {
	private final Locator locator;
	private final ApplicationDriver driver;

	public IOSElement(ApplicationDriver driver, Locator locator) {
		this.driver = driver;
		this.locator = locator;
	}

	@Override
	public boolean isPresent() {
		return driver.isPresent(this);
	}

	@Override
	public boolean isVisible() {
		return driver.isVisible(this);
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
		driver.touch(this);
	}

	public Locator locator() {
		return locator;
	}

	public ElementCommands whenPresent() {
		return new PolledElementCommands(driver.poll(), this, new IsPresent(this));
	}

	public ElementCommands whenVisible() {
		return new PolledElementCommands(driver.poll(), this, new IsVisible(this));
	}
	
	public ElementAssertion verify() {
		return new ElementAssertion(this);
	}

	@Override
	public ApplicationDriver driver() {
		return driver;
	}
}

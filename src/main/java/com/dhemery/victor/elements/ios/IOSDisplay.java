package com.dhemery.victor.elements.ios;

import com.dhemery.victor.driver.ApplicationDriver;

public class IOSDisplay {
	private final ApplicationDriver driver;
	public IOSDisplay(ApplicationDriver driver) {
		this.driver = driver;
	}

	public IOSElement button(String name) {
		return view("UINavigationItemButtonView", name);
	}

	public IOSElement label(String name) {
		return new IOSElement(driver, new IOSLocator("label", name));
	}

	public IOSElement view(String subtype, String name) {
		return new IOSElement(driver, new IOSLocator("view", subtype, name));
	}
}

package com.dhemery.victor.elements.ios;

import com.dhemery.victor.driver.ApplicationDriver;

public class IOSDisplay {
	private final ApplicationDriver driver;
	public IOSDisplay(ApplicationDriver driver) {
		this.driver = driver;
	}

	public IOSButton button(String name) {
		return new IOSButton(driver, "UINavigationItemButtonView", name);
	}

	public IOSLabel label(String name) {
		return new IOSLabel(driver, name);
	}

	public IOSView view(String type, String name) {
		return new IOSView(driver, type, name);
	}
}

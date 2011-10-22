package com.dhemery.victor.elements.ios;

import com.dhemery.victor.driver.ApplicationDriver;
import com.dhemery.victor.elements.Locator;

public class IOSDisplay {
	private final ApplicationDriver driver;
	public IOSDisplay(ApplicationDriver driver) {
		this.driver = driver;
	}

	public IOSElement element(Locator locator) {
		return new IOSElement(driver, locator);
	}

	public IOSElement element(String type, String name) {
		return element(new IOSLocator(type, name));
	}
}

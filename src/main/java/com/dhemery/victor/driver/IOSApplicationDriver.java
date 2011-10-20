package com.dhemery.victor.driver;

public interface IOSApplicationDriver {
	boolean elementExists(String locator);
	boolean elementIsVisible(String locator);
}

package com.dhemery.victor.driver;

import com.dhemery.victor.elements.IOSElement;

public interface IOSApplicationDriver {
	boolean elementExists(IOSElement element);
	boolean elementIsVisible(IOSElement element);
	void touch(IOSElement element);
	void waitUntilReady();
}

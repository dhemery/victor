package com.dhemery.victor.driver;

import com.dhemery.victor.elements.ios.IOSElement;

public interface IOSApplicationDriver {
	boolean isPresent(IOSElement element);
	boolean isVisible(IOSElement element);
	void touch(IOSElement element);
}

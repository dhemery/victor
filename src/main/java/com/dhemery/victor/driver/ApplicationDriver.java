package com.dhemery.victor.driver;

import com.dhemery.poller.Poll;
import com.dhemery.victor.elements.Element;

public interface ApplicationDriver {
	boolean isPresent(Element element);
	boolean isVisible(Element element);
	void touch(Element element);
	Poll poll();
}

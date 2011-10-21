package com.dhemery.victor.elements.polled;

import com.dhemery.victor.driver.Poll;
import com.dhemery.victor.elements.ElementConditions;
import com.dhemery.victor.elements.conditions.IsNotPresent;
import com.dhemery.victor.elements.conditions.IsNotVisible;
import com.dhemery.victor.elements.conditions.IsPresent;
import com.dhemery.victor.elements.conditions.IsVisible;
import com.dhemery.victor.elements.ios.IOSElement;


public class PolledIOSElementConditions implements ElementConditions {
	private final IOSElement element;
	private final Poll poll = new Poll();

	public PolledIOSElementConditions(IOSElement element) {
		this.element = element;
	}

	@Override
	public boolean isPresent() {
		poll.until(new IsPresent(element));
		return true;
	}
	
	@Override
	public boolean isNotPresent() {
		poll.until(new IsNotPresent(element));
		return true;
	}

	@Override
	public boolean isVisible() {
		poll.until(new IsVisible(element));
		return true;
	}

	@Override
	public boolean isNotVisible() {
		poll.until(new IsNotVisible(element));
		return true;
	}
}

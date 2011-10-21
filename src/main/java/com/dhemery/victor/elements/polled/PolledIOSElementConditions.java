package com.dhemery.victor.elements.polled;

import com.dhemery.victor.elements.ElementConditions;
import com.dhemery.victor.elements.conditions.IsNotPresent;
import com.dhemery.victor.elements.conditions.IsNotVisible;
import com.dhemery.victor.elements.conditions.IsPresent;
import com.dhemery.victor.elements.conditions.IsVisible;
import com.dhemery.victor.elements.ios.IOSElement;


public class PolledIOSElementConditions extends Polled implements ElementConditions {
	private final IOSElement element;

	public PolledIOSElementConditions(IOSElement element) {
		this.element = element;
	}

	@Override
	public boolean isPresent() {
		pollUntil(new IsPresent(element));
		return true;
	}
	
	@Override
	public boolean isNotPresent() {
		return pollUntil(new IsNotPresent(element));
	}

	@Override
	public boolean isVisible() {
		pollUntil(new IsVisible(element));
		return true;
	}

	@Override
	public boolean isNotVisible() {
		pollUntil(new IsNotVisible(element));
		return true;
	}
}

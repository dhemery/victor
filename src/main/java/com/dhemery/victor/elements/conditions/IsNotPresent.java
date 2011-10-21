package com.dhemery.victor.elements.conditions;

import com.dhemery.poller.Condition;
import com.dhemery.victor.elements.ios.IOSElement;

public class IsNotPresent implements Condition {
	private final IOSElement element;

	public IsNotPresent(IOSElement element) {
		this.element = element;
	}

	@Override
	public String describe() {
		return String.format("%s is not present", element.locator()) ;
	}

	@Override
	public boolean isSatisfied() {
		return element.isNotPresent();
	}
}

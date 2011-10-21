package com.dhemery.victor.elements.conditions;

import com.dhemery.poller.Condition;
import com.dhemery.victor.elements.ios.IOSElement;

public class IsPresent implements Condition {
	private final IOSElement element;

	public IsPresent(IOSElement element) {
		this.element = element;
	}

	@Override
	public String describe() {
		return String.format("%s is present", element.locator()) ;
	}

	@Override
	public boolean isSatisfied() {
		return element.isPresent();
	}
}

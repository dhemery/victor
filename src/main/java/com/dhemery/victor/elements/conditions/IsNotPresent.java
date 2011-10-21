package com.dhemery.victor.elements.conditions;

import com.dhemery.poller.Condition;
import com.dhemery.victor.elements.Element;

public class IsNotPresent implements Condition {
	private final Element element;

	public IsNotPresent(Element element) {
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

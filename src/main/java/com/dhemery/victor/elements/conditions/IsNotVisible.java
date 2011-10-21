package com.dhemery.victor.elements.conditions;

import com.dhemery.poller.Condition;
import com.dhemery.victor.elements.Element;

public class IsNotVisible implements Condition {
	private final Element element;

	public IsNotVisible(Element element) {
		this.element = element;
	}

	@Override
	public String describe() {
		return String.format("[%s] is not visible", element.locator());
	}

	@Override
	public boolean isSatisfied() {
		return element.isNotVisible();
	}
}

package com.dhemery.victor.elements.conditions;

import com.dhemery.poller.Condition;
import com.dhemery.victor.elements.Element;

public class IsVisible implements Condition {
	private final Element element;

	public IsVisible(Element element) {
		this.element = element;
	}

	@Override
	public String describe() {
		return String.format("%s is visible", element.locator());
	}

	@Override
	public boolean isSatisfied() {
		return element.isVisible();
	}
}

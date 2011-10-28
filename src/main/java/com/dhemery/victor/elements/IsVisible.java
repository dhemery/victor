package com.dhemery.victor.elements;

import com.dhemery.poller.Condition;
import com.dhemery.victor.Element;

public class IsVisible implements Condition {
	private final Element element;

	public IsVisible(Element element) {
		this.element = element;
	}

	@Override
	public String describe() {
		return String.format("[%s] is visible", element.query());
	}

	@Override
	public boolean isSatisfied() {
		return element.isVisible();
	}
}

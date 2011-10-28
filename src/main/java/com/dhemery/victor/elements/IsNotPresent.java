package com.dhemery.victor.elements;

import com.dhemery.poller.Condition;
import com.dhemery.victor.Element;

public class IsNotPresent implements Condition {
	private final Element element;

	public IsNotPresent(Element element) {
		this.element = element;
	}

	@Override
	public String describe() {
		return String.format("[%s] is not present", element.query()) ;
	}

	@Override
	public boolean isSatisfied() {
		return element.isNotPresent();
	}
}

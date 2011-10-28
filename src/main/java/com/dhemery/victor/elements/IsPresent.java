package com.dhemery.victor.elements;

import com.dhemery.poller.Condition;
import com.dhemery.victor.Element;

public class IsPresent implements Condition {
	private final Element element;

	public IsPresent(Element element) {
		this.element = element;
	}

	@Override
	public String describe() {
		return String.format("[%s] is present", element.query()) ;
	}

	@Override
	public boolean isSatisfied() {
		return element.isPresent();
	}
}

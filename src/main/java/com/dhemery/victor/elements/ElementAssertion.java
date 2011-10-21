package com.dhemery.victor.elements;

import com.dhemery.poller.Condition;
import com.dhemery.victor.elements.conditions.IsNotPresent;
import com.dhemery.victor.elements.conditions.IsNotVisible;
import com.dhemery.victor.elements.conditions.IsPresent;
import com.dhemery.victor.elements.conditions.IsVisible;

public class ElementAssertion implements ElementConditions {
	private final Element element;

	public ElementAssertion(Element element) {
		this.element = element;
	}

	public ElementConditions eventually() {
		return new PolledElementConditions(element);
	}

	@Override
	public boolean isNotPresent() {
		return trueOrThrow(new IsNotPresent(element));
	}

	@Override
	public boolean isNotVisible() {
		return trueOrThrow(new IsNotVisible(element));
	}

	@Override
	public boolean isPresent() {
		return trueOrThrow(new IsPresent(element));
	}

	@Override
	public boolean isVisible() {
		return trueOrThrow(new IsVisible(element));
	}

	private boolean trueOrThrow(Condition condition) {
		if(condition.isSatisfied()) return true;
		throw new ElementAssertionException(String.format("Expected %s", condition.describe()));
	}
}

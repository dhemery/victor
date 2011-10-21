package com.dhemery.victor.elements;

import static org.junit.Assert.assertTrue;

public class ElementAssertion implements ElementConditions {
	private final Element element;

	public ElementAssertion(Element element) {
		this.element = element;
	}

	@Override
	public boolean isPresent() {
		assertTrue(element.isPresent());
		return true;
	}

	@Override
	public boolean isVisible() {
		assertTrue(element.isVisible());
		return true;
	}

	@Override
	public boolean isNotPresent() {
		assertTrue(element.isNotPresent());
		return true;
	}

	@Override
	public boolean isNotVisible() {
		assertTrue(element.isNotVisible());
		return true;
	}

	public ElementConditions eventually() {
		return new PolledElementConditions(element);
	}
}

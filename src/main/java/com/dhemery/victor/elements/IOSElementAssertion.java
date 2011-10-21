package com.dhemery.victor.elements;

import static org.junit.Assert.*;
public class IOSElementAssertion implements IOSElementConditions {
	private final IOSElement element;

	public IOSElementAssertion(IOSElement element) {
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
}

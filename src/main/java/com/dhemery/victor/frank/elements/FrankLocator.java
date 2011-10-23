package com.dhemery.victor.frank.elements;

import com.dhemery.victor.elements.Locator;

public class FrankLocator implements Locator {
	private final String mark;
	private final String type;

	public FrankLocator(String type, String mark) {
		this.type = type;
		this.mark = mark;
	}
	
	@Override
	public String toString() {
		return String.format("%s marked:'%s'", type, mark);
	}
}

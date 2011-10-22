package com.dhemery.victor.elements.ios;

import com.dhemery.victor.elements.Locator;

public class IOSLocator implements Locator {
	private final String mark;
	private final String type;

	public IOSLocator(String type, String mark) {
		this.type = type;
		this.mark = mark;
	}
	
	@Override
	public String toString() {
		return String.format("%s marked:'%s'", type, mark);
	}
}

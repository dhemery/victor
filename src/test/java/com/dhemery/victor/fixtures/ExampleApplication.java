package com.dhemery.victor.fixtures;

import com.dhemery.victor.elements.ElementFactory;

public class ExampleApplication {
	private final ElementFactory elementFactory;

	public ExampleApplication(ElementFactory elementFactory) {
		this.elementFactory = elementFactory;
	}
	
	public DetailDisplay detailDisplay() {
		return new DetailDisplay(elementFactory);
	}

	public MasterDisplay masterDisplay() {
		return new MasterDisplay(elementFactory);
	}
}

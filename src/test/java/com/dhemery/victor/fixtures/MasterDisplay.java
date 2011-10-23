package com.dhemery.victor.fixtures;

import com.dhemery.victor.elements.Element;
import com.dhemery.victor.elements.ElementFactory;

public class MasterDisplay {
	private final ElementFactory factory;

	public MasterDisplay(ElementFactory factory) {
		this.factory = factory;
	}

	public Element detailLabel() {
		return factory.element("label", "Detail");
	}

	public Element masterView() {
		return factory.element("navigationItemView", "Master");
	}
}

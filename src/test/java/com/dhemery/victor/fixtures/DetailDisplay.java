package com.dhemery.victor.fixtures;

import com.dhemery.victor.elements.Element;
import com.dhemery.victor.elements.ElementFactory;

public class DetailDisplay {
	private final ElementFactory elementFactory; 

	public DetailDisplay(ElementFactory elementFactory) {
		this.elementFactory = elementFactory;
	}

	public Element masterButton() {
		return elementFactory.element("navigationItemButtonView", "Master");
	}

	public Element detailView() {
		return elementFactory.element("navigationItemView", "Detail");
	}
}

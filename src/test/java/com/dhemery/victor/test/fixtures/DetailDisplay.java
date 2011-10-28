package com.dhemery.victor.test.fixtures;

import com.dhemery.victor.Application;
import com.dhemery.victor.Element;

public class DetailDisplay {
	private final Application application; 

	public DetailDisplay(Application application) {
		this.application = application;
	}

	public Element masterButton() {
		return application.element("navigationItemButtonView", "Master");
	}

	public Element detailView() {
		return application.element("navigationItemView", "Detail");
	}
}

package com.dhemery.victor.test.fixtures;

import com.dhemery.victor.Application;
import com.dhemery.victor.Element;

public class MasterDisplay {
	private final Application application;

	public MasterDisplay(Application application) {
		this.application = application;
	}

	public Element detailLabel() {
		return application.element("label", "Detail");
	}

	public Element masterView() {
		return application.element("navigationItemView", "Master");
	}
}

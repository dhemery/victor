package com.dhemery.victor.fixtures;

import com.dhemery.victor.driver.ApplicationDriver;
import com.dhemery.victor.elements.Element;
import com.dhemery.victor.elements.Locator;
import com.dhemery.victor.elements.ios.IOSDisplay;
import com.dhemery.victor.elements.ios.IOSLocator;

public class MasterDisplay extends IOSDisplay {
	private static final Locator MASTER_VIEW = new IOSLocator("navigationItemView", "Master");
	private static final Locator DETAIL_LABEL = new IOSLocator("label", "Detail");

	public MasterDisplay(ApplicationDriver app) {
		super(app);
	}

	public Element detailLabel() {
		return element(DETAIL_LABEL);
	}

	public Element masterView() {
		return element(MASTER_VIEW);
	}

}

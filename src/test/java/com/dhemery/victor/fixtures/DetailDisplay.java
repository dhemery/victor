package com.dhemery.victor.fixtures;

import com.dhemery.victor.driver.ApplicationDriver;
import com.dhemery.victor.elements.Element;
import com.dhemery.victor.elements.Locator;
import com.dhemery.victor.elements.ios.IOSDisplay;
import com.dhemery.victor.elements.ios.IOSLocator;

public class DetailDisplay extends IOSDisplay {
	private static final Locator MASTER_BUTTON = new IOSLocator("navigationItemButtonView", "Master"); 
	private static final Locator DETAIL_VIEW = new IOSLocator("navigationItemView", "Detail"); 

	public DetailDisplay(ApplicationDriver app) {
		super(app);
	}

	public Element masterButton() {
		return element(MASTER_BUTTON);
	}

	public Element detailView() {
		return element(DETAIL_VIEW);
	}
}

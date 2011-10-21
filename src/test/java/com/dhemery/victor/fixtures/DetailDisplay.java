package com.dhemery.victor.fixtures;

import com.dhemery.victor.driver.ApplicationDriver;
import com.dhemery.victor.elements.Element;
import com.dhemery.victor.elements.ios.IOSDisplay;

public class DetailDisplay extends IOSDisplay {
	public DetailDisplay(ApplicationDriver app) {
		super(app);
	}

	public Element masterButton() {
		return button("Master");
	}

	public Element detailView() {
		return view("UINavigationItemView", "Detail");
	}
}

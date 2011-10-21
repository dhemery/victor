package com.dhemery.victor.fixtures;

import com.dhemery.victor.driver.ApplicationDriver;
import com.dhemery.victor.elements.ios.IOSButton;
import com.dhemery.victor.elements.ios.IOSDisplay;
import com.dhemery.victor.elements.ios.IOSView;

public class DetailDisplay extends IOSDisplay {
	public DetailDisplay(ApplicationDriver app) {
		super(app);
	}

	public IOSButton masterButton() {
		return button("Master");
	}

	public IOSView detailView() {
		return view("UINavigationItemView", "Detail");
	}
}

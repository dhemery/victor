package com.dhemery.victor.fixtures;

import com.dhemery.victor.driver.IOSApplicationDriver;
import com.dhemery.victor.elements.IOSButton;
import com.dhemery.victor.elements.IOSDisplay;
import com.dhemery.victor.elements.IOSView;

public class DetailDisplay extends IOSDisplay {
	public DetailDisplay(IOSApplicationDriver app) {
		super(app);
	}

	public IOSButton masterButton() {
		return button("Master");
	}

	public IOSView detailView() {
		return view("UINavigationItemView", "Detail");
	}
}

package com.dhemery.victor.fixtures;

import com.dhemery.victor.driver.ApplicationDriver;
import com.dhemery.victor.elements.ios.IOSDisplay;
import com.dhemery.victor.elements.ios.IOSLabel;
import com.dhemery.victor.elements.ios.IOSView;

public class MasterDisplay extends IOSDisplay {
	public MasterDisplay(ApplicationDriver app) {
		super(app);
	}

	public IOSLabel detailLabel() {
		return label("Detail");
	}

	public IOSView masterView() {
		return view("UINavigationItemView", "Master");
	}

}

package com.dhemery.victor.fixtures;

import com.dhemery.victor.driver.IOSApplicationDriver;
import com.dhemery.victor.elements.ios.IOSDisplay;
import com.dhemery.victor.elements.ios.IOSLabel;
import com.dhemery.victor.elements.ios.IOSView;

public class MasterDisplay extends IOSDisplay {
	public MasterDisplay(IOSApplicationDriver app) {
		super(app);
	}

	public IOSLabel detailLabel() {
		return label("Detail");
	}

	public IOSView masterView() {
		return view("UINavigationItemView", "Master");
	}

}

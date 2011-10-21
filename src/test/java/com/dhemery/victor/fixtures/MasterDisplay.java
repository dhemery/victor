package com.dhemery.victor.fixtures;

import com.dhemery.victor.driver.IOSApplicationDriver;
import com.dhemery.victor.elements.IOSDisplay;
import com.dhemery.victor.elements.IOSLabel;
import com.dhemery.victor.elements.IOSView;

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

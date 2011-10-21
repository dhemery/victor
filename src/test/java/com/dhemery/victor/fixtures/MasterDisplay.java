package com.dhemery.victor.fixtures;

import com.dhemery.victor.driver.ApplicationDriver;
import com.dhemery.victor.elements.Element;
import com.dhemery.victor.elements.ios.IOSDisplay;

public class MasterDisplay extends IOSDisplay {
	public MasterDisplay(ApplicationDriver app) {
		super(app);
	}

	public Element detailLabel() {
		return label("Detail");
	}

	public Element masterView() {
		return view("UINavigationItemView", "Master");
	}

}

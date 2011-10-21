package com.dhemery.victor.elements.ios;

import com.dhemery.victor.driver.ApplicationDriver;

public class IOSView extends IOSElement {
	public IOSView(ApplicationDriver app, String type, String name) {
		super(app, String.format("view:'%s' marked:'%s'", type, name));
	}

}

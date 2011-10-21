package com.dhemery.victor.elements;

import com.dhemery.victor.driver.IOSApplicationDriver;

public class IOSView extends IOSElement {
	public IOSView(IOSApplicationDriver app, String type, String name) {
		super(app, String.format("view:'%s' marked:'%s'", type, name));
	}

}

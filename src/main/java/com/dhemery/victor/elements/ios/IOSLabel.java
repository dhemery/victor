package com.dhemery.victor.elements.ios;

import com.dhemery.victor.driver.ApplicationDriver;

public class IOSLabel extends IOSElement {
	public IOSLabel(ApplicationDriver app, String mark) {
		super(app, String.format("label marked:'%s'", mark));
	}
}

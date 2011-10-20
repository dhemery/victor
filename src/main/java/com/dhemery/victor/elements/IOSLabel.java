package com.dhemery.victor.elements;

import com.dhemery.victor.driver.IOSApplicationDriver;

public class IOSLabel extends IOSElement {
	public IOSLabel(IOSApplicationDriver app, String mark) {
		super(app, String.format("label marked:'%s'", mark));
	}
}

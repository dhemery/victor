package com.dhemery.victor.elements;

import com.dhemery.victor.driver.IOSApplicationDriver;

public class IOSDisplay {
	private final IOSApplicationDriver app;
	public IOSDisplay(IOSApplicationDriver app) {
		this.app = app;
	}

	protected IOSApplicationDriver app() {
		return app;
	}

	public IOSLabel label(String name) {
		return new IOSLabel(app, "Details");
	}
}

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

	public IOSButton button(String name) {
		return new IOSButton(app, "UINavigationItemView", name);
	}

	public IOSLabel label(String name) {
		return new IOSLabel(app, name);
	}

	public IOSView view(String type, String name) {
		return new IOSView(app, type, name);
	}
}

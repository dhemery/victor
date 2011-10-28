package com.dhemery.victor.application;

import com.dhemery.poller.Condition;
import com.dhemery.victor.Application;
import com.dhemery.victor.Application.Orientation;

public class HasOrientation implements Condition {
	private final Application application;
	private final Orientation orientation;

	public HasOrientation(Application application, Orientation orientation) {
		this.application = application;
		this.orientation = orientation;
	}

	@Override
	public String describe() {
		return String.format("application is in %s orientation.", orientation.toString().toLowerCase());
	}

	@Override
	public boolean isSatisfied() {
		return application.hasOrientation(orientation);
	}
}

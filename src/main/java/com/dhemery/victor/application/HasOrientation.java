package com.dhemery.victor.application;

import com.dhemery.poller.Condition;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ApplicationDriver.Orientation;

/**
 * A Condition that determines whether the application has a given orientation (portrait or landscape).
 * @author Dale Emery
 *
 */
public class HasOrientation extends Condition {
	private final ApplicationDriver application;
	private final Orientation orientation;

	public HasOrientation(ApplicationDriver application, Orientation orientation) {
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

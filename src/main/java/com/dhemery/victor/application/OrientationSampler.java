package com.dhemery.victor.application;

import com.dhemery.matchers.Feature;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ApplicationDriver.Orientation;

public class OrientationSampler extends Feature<ApplicationDriver, Orientation> {
	@Override
	public String name() {
		return "orientation";
	}

	@Override
	public Orientation valueOn(ApplicationDriver subject) {
		return subject.orientation();
	}
}

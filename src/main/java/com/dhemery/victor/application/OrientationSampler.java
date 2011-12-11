package com.dhemery.victor.application;

import org.hamcrest.Description;

import com.dhemery.matchers.Sampler;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ApplicationDriver.Orientation;

public class OrientationSampler implements Sampler<ApplicationDriver, Orientation> {
	private Orientation orientation;

	@Override
	public void describeTo(Description description) {
		description.appendText("orientation");
	}

	@Override
	public void sample(ApplicationDriver application) {
		orientation = application.orientation();
	}

	@Override
	public Orientation value() {
		return orientation;
	}

}

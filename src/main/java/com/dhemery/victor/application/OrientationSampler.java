package com.dhemery.victor.application;

import org.hamcrest.Description;

import com.dhemery.pollable.Sampler;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ApplicationDriver.Orientation;

public class OrientationSampler extends Sampler<ApplicationDriver, Orientation> {
	@Override
	public void describeTo(Description description) {
		description.appendText("orientation");
	}

	@Override
	protected Orientation sample(ApplicationDriver application) {
		return application.orientation();
	}
}

package com.dhemery.victor;

import com.dhemery.matchers.Sampler;
import com.dhemery.victor.application.OrientationSampler;

public class ApplicationAttributes {
	public static Sampler<ApplicationDriver, ApplicationDriver.Orientation> orientation() {
		return new OrientationSampler();
	}
}

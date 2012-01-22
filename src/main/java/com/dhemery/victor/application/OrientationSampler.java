package com.dhemery.victor.application;

import com.dhemery.sentences.Query;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ApplicationDriver.Orientation;

public class OrientationSampler extends Query<ApplicationDriver, Orientation> {
	@Override
	public String name() {
		return "orientation";
	}

	@Override
	public Orientation query(ApplicationDriver subject) {
		return subject.orientation();
	}
}

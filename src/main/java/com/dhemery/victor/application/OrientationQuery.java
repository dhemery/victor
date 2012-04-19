package com.dhemery.victor.application;

import com.dhemery.polling.Query;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ApplicationDriver.Orientation;

public class OrientationQuery extends Query<ApplicationDriver, Orientation> {
	@Override
	public String name() {
		return "orientation";
	}

	@Override
	public Orientation query(ApplicationDriver subject) {
		return subject.orientation();
	}
}

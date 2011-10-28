package com.dhemery.victor.application;

import com.dhemery.poller.Poll;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ApplicationDriver.Orientation;

public class PolledApplicationConditions implements ApplicationConditions {
	private final ApplicationDriver application;
	private final Poll poll;

	public PolledApplicationConditions(ApplicationDriver application, Poll poll) {
		this.application = application;
		this.poll = poll;
	}
	
	@Override
	public boolean hasOrientation(Orientation orientation) {
		poll.until(new HasOrientation(application, orientation));
		return true;
	}
}

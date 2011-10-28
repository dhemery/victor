package com.dhemery.victor.application;

import com.dhemery.poller.Poll;
import com.dhemery.victor.Application;
import com.dhemery.victor.Application.Orientation;

public class PolledApplicationConditions implements ApplicationConditions {
	private final Application application;
	private final Poll poll;

	public PolledApplicationConditions(Application application, Poll poll) {
		this.application = application;
		this.poll = poll;
	}
	
	@Override
	public boolean hasOrientation(Orientation orientation) {
		poll.until(new HasOrientation(application, orientation));
		return true;
	}
}

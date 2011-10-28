package com.dhemery.victor.application;

import com.dhemery.poller.Poll;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ApplicationDriver.Orientation;


public class ApplicationAssertion implements ApplicationConditions {
	private final ApplicationDriver application;
	private final Poll poll;

	public ApplicationAssertion(ApplicationDriver application, Poll poll) {
		this.application = application;
		this.poll = poll;		
	}
	
	public ApplicationConditions eventually() {
		return new PolledApplicationConditions(application, poll);
	}

	@Override
	public boolean hasOrientation(Orientation orientation) {
		new HasOrientation(application, orientation).requireSatisfied();
		return true;
	}
}

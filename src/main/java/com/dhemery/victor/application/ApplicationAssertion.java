package com.dhemery.victor.application;

import com.dhemery.poller.Condition;
import com.dhemery.poller.Poll;
import com.dhemery.victor.Application;
import com.dhemery.victor.Application.Orientation;


public class ApplicationAssertion implements ApplicationConditions {
	private final Application application;
	private final Poll poll;

	public ApplicationAssertion(Application application, Poll poll) {
		this.application = application;
		this.poll = poll;		
	}
	
	public ApplicationConditions eventually() {
		return new PolledApplicationConditions(application, poll);
	}

	@Override
	public boolean hasOrientation(Orientation orientation) {
		return trueOrThrow(new HasOrientation(application, orientation));
	}

	private boolean trueOrThrow(Condition condition) {
		if(condition.isSatisfied()) return true;
		throw new ApplicationAssertionException(String.format("Expected %s", condition.describe()));
	}
}

package com.dhemery.victor.views;

import com.dhemery.poller.Poll;
import com.dhemery.victor.ViewDriver;


public class PolledElementConditions implements ElementConditions {
	private final ViewDriver element;
	private final Poll poll;

	public PolledElementConditions(ViewDriver element, Poll poll) {
		this.element = element;
		this.poll = poll;
	}

	@Override
	public boolean isPresent() {
		poll.until(new IsPresent(element));
		return true;
	}
	
	@Override
	public boolean isNotPresent() {
		poll.until(new IsNotPresent(element));
		return true;
	}

	@Override
	public boolean isVisible() {
		poll.until(new IsVisible(element));
		return true;
	}

	@Override
	public boolean isNotVisible() {
		poll.until(new IsNotVisible(element));
		return true;
	}
}

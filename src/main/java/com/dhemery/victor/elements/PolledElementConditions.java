package com.dhemery.victor.elements;

import com.dhemery.poller.Poll;
import com.dhemery.victor.elements.conditions.IsNotPresent;
import com.dhemery.victor.elements.conditions.IsNotVisible;
import com.dhemery.victor.elements.conditions.IsPresent;
import com.dhemery.victor.elements.conditions.IsVisible;


public class PolledElementConditions implements ElementConditions {
	private final Element element;
	private final Poll poll;

	public PolledElementConditions(Element element, Poll poll) {
		this.poll = poll;
		this.element = element;
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

package com.dhemery.victor.views;

import com.dhemery.poller.Poll;
import com.dhemery.victor.ViewDriver;

public class ElementAssertion implements ElementConditions {
	private final ViewDriver element;
	private final Poll poll;

	public ElementAssertion(ViewDriver element, Poll poll) {
		this.element = element;
		this.poll = poll;
	}

	public ElementConditions eventually() {
		return new PolledElementConditions(element, poll);
	}

	@Override
	public boolean isNotPresent() {
		new IsNotPresent(element).requireSatisfied();
		return true;
	}

	@Override
	public boolean isNotVisible() {
		new IsNotVisible(element).requireSatisfied();
		return true;
	}

	@Override
	public boolean isPresent() {
		new IsPresent(element).requireSatisfied();
		return true;
	}

	@Override
	public boolean isVisible() {
		new IsVisible(element).requireSatisfied();
		return true;
	}
}

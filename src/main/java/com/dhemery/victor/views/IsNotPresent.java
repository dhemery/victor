package com.dhemery.victor.views;

import com.dhemery.poller.Condition;
import com.dhemery.victor.ViewDriver;

public class IsNotPresent extends Condition {
	private final ViewDriver element;

	public IsNotPresent(ViewDriver element) {
		this.element = element;
	}

	@Override
	public String describe() {
		return String.format("[%s] is not present", element.query()) ;
	}

	@Override
	public boolean isSatisfied() {
		return element.isNotPresent();
	}
}

package com.dhemery.victor.views;

import com.dhemery.poller.Condition;
import com.dhemery.victor.ViewDriver;

public class IsPresent extends Condition {
	private final ViewDriver element;

	public IsPresent(ViewDriver element) {
		this.element = element;
	}

	@Override
	public String describe() {
		return String.format("[%s] is present", element.query()) ;
	}

	@Override
	public boolean isSatisfied() {
		return element.isPresent();
	}
}

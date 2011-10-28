package com.dhemery.victor.views;

import com.dhemery.poller.Condition;
import com.dhemery.victor.ViewDriver;

public class IsNotVisible extends Condition {
	private final ViewDriver element;

	public IsNotVisible(ViewDriver element) {
		this.element = element;
	}

	@Override
	public String describe() {
		return String.format("[%s] is not visible", element.query());
	}

	@Override
	public boolean isSatisfied() {
		return element.isNotVisible();
	}
}

package com.dhemery.victor.views;

import com.dhemery.poller.Condition;
import com.dhemery.victor.ViewDriver;

public class IsVisible extends Condition {
	private final ViewDriver element;

	public IsVisible(ViewDriver element) {
		this.element = element;
	}

	@Override
	public String describe() {
		return String.format("[%s] is visible", element.query());
	}

	@Override
	public boolean isSatisfied() {
		return element.isVisible();
	}
}

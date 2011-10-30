package com.dhemery.victor.view;

import com.dhemery.poller.Condition;
import com.dhemery.victor.ViewDriver;

/**
 * A condition that is satisfied when the view is visible.
 * @author Dale Emery
 *
 */
public class IsVisible extends Condition {
	private final ViewDriver view;

	public IsVisible(ViewDriver view) {
		this.view = view;
	}

	@Override
	public String describe() {
		return String.format("[%s] is visible", view.query());
	}

	@Override
	public boolean isSatisfied() {
		return view.isVisible();
	}
}

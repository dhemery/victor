package com.dhemery.victor.view;

import com.dhemery.poller.Condition;
import com.dhemery.victor.ViewDriver;

/**
 * A condition that is satisfied when the view is not visible.
 * @author Dale Emery
 *
 */
public class IsNotVisible extends Condition {
	private final ViewDriver view;

	public IsNotVisible(ViewDriver view) {
		this.view = view;
	}

	@Override
	public String describe() {
		return String.format("[%s] is not visible", view.query());
	}

	@Override
	public boolean isSatisfied() {
		return view.isNotVisible();
	}
}

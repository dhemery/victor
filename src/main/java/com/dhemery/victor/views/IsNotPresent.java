package com.dhemery.victor.views;

import com.dhemery.poller.Condition;
import com.dhemery.victor.ViewDriver;

/**
 * A condition that is satisfied when the view is not present.
 * @author Dale Emery
 *
 */
public class IsNotPresent extends Condition {
	private final ViewDriver view;

	public IsNotPresent(ViewDriver view) {
		this.view = view;
	}

	@Override
	public String describe() {
		return String.format("[%s] is not present", view.query()) ;
	}

	@Override
	public boolean isSatisfied() {
		return view.isNotPresent();
	}
}

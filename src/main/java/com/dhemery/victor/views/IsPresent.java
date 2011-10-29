package com.dhemery.victor.views;

import com.dhemery.poller.Condition;
import com.dhemery.victor.ViewDriver;

/**
 * A condition that is satisfied when the view is present.
 * @author Dale Emery
 *
 */
public class IsPresent extends Condition {
	private final ViewDriver view;

	public IsPresent(ViewDriver view) {
		this.view = view;
	}

	@Override
	public String describe() {
		return String.format("[%s] is present", view.query()) ;
	}

	@Override
	public boolean isSatisfied() {
		return view.isPresent();
	}
}

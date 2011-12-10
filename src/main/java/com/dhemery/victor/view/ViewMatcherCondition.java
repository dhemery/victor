package com.dhemery.victor.view;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import com.dhemery.poller.Condition;
import com.dhemery.victor.ViewDriver;

public class ViewMatcherCondition extends Condition {
	private final ViewDriver view;
	private final Matcher<ViewDriver> matcher;

	public ViewMatcherCondition(ViewDriver view, Matcher<ViewDriver> matcher) {
		this.view = view;
		this.matcher = matcher;
	}

	@Override
	public String describe() {
		Description description = new StringDescription();
		matcher.describeTo(description);
		return description.toString();
	}

	@Override
	public boolean isSatisfied() {
		return matcher.matches(view);
	}
}

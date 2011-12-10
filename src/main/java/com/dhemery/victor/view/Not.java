package com.dhemery.victor.view;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import com.dhemery.victor.ViewDriver;

public class Not extends TypeSafeMatcher<ViewDriver> {
	private final Matcher<ViewDriver> matcher;

	public Not(Matcher<ViewDriver> matcher) {
		this.matcher = matcher;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("not ").appendDescriptionOf(matcher);
	}

	@Override
	protected void describeMismatchSafely(ViewDriver view, Description mismatchDescription) {
		mismatchDescription.appendDescriptionOf(view)
							.appendText(" ")
							.appendDescriptionOf(matcher);
	}

	@Override
	protected boolean matchesSafely(ViewDriver view) {
		return !matcher.matches(view);
	}
}

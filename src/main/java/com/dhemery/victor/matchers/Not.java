package com.dhemery.victor.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeMatcher;

public class Not<T extends SelfDescribing> extends TypeSafeMatcher<T> {
	private final Matcher<T> matcher;

	public Not(Matcher<T> matcher) {
		this.matcher = matcher;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("not ").appendDescriptionOf(matcher);
	}

	@Override
	protected void describeMismatchSafely(T item, Description mismatchDescription) {
		mismatchDescription.appendDescriptionOf(item)
							.appendText(" ")
							.appendDescriptionOf(matcher);
	}

	@Override
	protected boolean matchesSafely(T item) {
		return !matcher.matches(item);
	}
	
	public static <T extends SelfDescribing> Matcher<T> not(Matcher<T> matcher) {
		return new Not<T>(matcher);
	}
}

package com.dhemery.victor.frank;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class Ready extends TypeSafeMatcher<FrankClient> {
	@Override
	public void describeTo(Description description) {
		description.appendText("ready to respond to requests");
	}

	@Override
	protected boolean matchesSafely(FrankClient frank) {
		return frank.isReady();
	}
	
	@Override
	protected void describeMismatchSafely(FrankClient item, Description mismatchDescription) {
		mismatchDescription.appendDescriptionOf(item)
							.appendText(" is not ready to respond to requests");
	}
	
	public static Matcher<FrankClient> ready() {
		return new Ready();
	}
}

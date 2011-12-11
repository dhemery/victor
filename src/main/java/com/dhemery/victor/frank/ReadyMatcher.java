package com.dhemery.victor.frank;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ReadyMatcher extends TypeSafeMatcher<FrankClient> {
	@Override
	public void describeTo(Description description) {
		description.appendText("responds to requests");
	}

	@Override
	protected boolean matchesSafely(FrankClient frank) {
		return frank.isReady();
	}
	
	@Override
	protected void describeMismatchSafely(FrankClient item, Description mismatchDescription) {
		mismatchDescription.appendDescriptionOf(item)
							.appendText(" does not yet respond to requests");
	}
	
	public static ReadyMatcher ready() {
		return new ReadyMatcher();
	}
}

package com.dhemery.victor.application;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.Matchers.equalTo;

import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ApplicationDriver.Orientation;

public class OrientationMatcher extends TypeSafeMatcher<ApplicationDriver> {
	private final Matcher<Orientation> matcher;

	public OrientationMatcher(Orientation orientation) {
		this.matcher = equalTo(orientation);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(" has orientation ")
					.appendDescriptionOf(matcher);
	}

	@Override
	protected boolean matchesSafely(ApplicationDriver application) {
		return matcher.matches(application.orientation());
	}

	@Override
	protected void describeMismatchSafely(ApplicationDriver application, Description mismatchDescription) {
		mismatchDescription.appendDescriptionOf(application)
							.appendText(" does not have orientation ")
							.appendDescriptionOf(matcher);
	}
	
	public static OrientationMatcher orientation(Orientation orientation) {
		return new OrientationMatcher(orientation);
	}
}

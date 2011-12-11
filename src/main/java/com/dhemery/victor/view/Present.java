package com.dhemery.victor.view;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import com.dhemery.victor.ViewDriver;

public class Present extends TypeSafeMatcher<ViewDriver> {

	@Override
	public void describeTo(Description description) {
		description.appendText("present");
	}

	@Override
	protected boolean matchesSafely(ViewDriver view) {
		return view.isPresent();
	}
	
	public static Matcher<ViewDriver> present() {
		return new Present();
	}
}
package com.dhemery.victor.view;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import com.dhemery.victor.ViewDriver;

public class Visible extends TypeSafeMatcher<ViewDriver> {

	@Override
	public void describeTo(Description description) {
		description.appendText("visible");
	}

	@Override
	protected boolean matchesSafely(ViewDriver view) {
		return view.isVisible();
	}
	
	public static Matcher<ViewDriver> visible() {
		return new Visible();
	}
}
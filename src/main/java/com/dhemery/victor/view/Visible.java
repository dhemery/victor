package com.dhemery.victor.view;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import com.dhemery.victor.ViewDriver;

import java.util.List;

public class Visible extends TypeSafeMatcher<ViewDriver> {

	@Override
	public void describeTo(Description description) {
		description.appendText("visible");
	}

	@Override
	protected boolean matchesSafely(ViewDriver view) {
        List<String> results = view.call("isHidden");
        if (results.size() != 1) return false;
		return !Boolean.parseBoolean(results.get(0));
	}
}

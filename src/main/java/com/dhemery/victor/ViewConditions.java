package com.dhemery.victor;

import org.hamcrest.Matcher;

import com.dhemery.victor.view.Present;
import com.dhemery.victor.view.Visible;

public class ViewConditions {
	public static Matcher<ViewDriver> present() {
		return new Present();
	}

	public static Matcher<ViewDriver> visible() {
		return new Visible();
	}
}

package com.dhemery.victor.test.fixtures;

import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ViewDriver;

public class DetailDisplay {
	private final ApplicationDriver application; 

	public DetailDisplay(ApplicationDriver application) {
		this.application = application;
	}

	public ViewDriver masterButton() {
		return application.view("*");
	}

	public ViewDriver detailView() {
		return application.view("*");
	}
}

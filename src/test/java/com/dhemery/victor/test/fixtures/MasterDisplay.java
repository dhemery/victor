package com.dhemery.victor.test.fixtures;

import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ViewDriver;

public class MasterDisplay {
	private final ApplicationDriver application;

	public MasterDisplay(ApplicationDriver application) {
		this.application = application;
	}

	public ViewDriver detailLabel() {
		return application.view("*");
	}

	public ViewDriver masterView() {
		return application.view("*");
	}
}

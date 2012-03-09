package com.dhemery.victor.test.fixtures;

import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.IgorSelector;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.ViewSelector;

public class MasterDisplay {
	private static final ViewSelector DETAIL_LABEL = new IgorSelector("UILabel");
	private static final ViewSelector MASTER_VIEW = new IgorSelector("UINavigationItemView");
	private final ApplicationDriver application;

	public MasterDisplay(ApplicationDriver application) {
		this.application = application;
	}

	public ViewDriver detailLabel() {
		return application.view(DETAIL_LABEL);
	}

	public ViewDriver masterView() {
		return application.view(MASTER_VIEW);
	}
}

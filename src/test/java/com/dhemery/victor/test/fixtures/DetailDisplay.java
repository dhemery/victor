package com.dhemery.victor.test.fixtures;

import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.IgorSelector;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.ViewSelector;

public class DetailDisplay {
	private static final ViewSelector DETAIL_VIEW = new IgorSelector("UINavigationItemView");
	private static final ViewSelector MASTER_BUTTON = new IgorSelector("UINavigationItemButtonView");
	private final ApplicationDriver application; 

	public DetailDisplay(ApplicationDriver application) {
		this.application = application;
	}

	public ViewDriver masterButton() {
		return application.view(MASTER_BUTTON);
	}

	public ViewDriver detailView() {
		return application.view(DETAIL_VIEW);
	}
}

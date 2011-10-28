package com.dhemery.victor.tests;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.test.VictorTest;
import com.dhemery.victor.test.fixtures.DetailDisplay;
import com.dhemery.victor.test.fixtures.MasterDisplay;

public class ApplicationTests extends VictorTest {
	private MasterDisplay master;
	private DetailDisplay detail;
	private ViewDriver masterView;
	private ViewDriver detailLabel;
	private ViewDriver detailView;
	private ViewDriver masterButton;

	@Before
	public void setUp() {
		master = new MasterDisplay(application());
		detail = new DetailDisplay(application());		
		masterView = master.masterView();
		detailLabel = master.detailLabel();
		detailView = detail.detailView();
		masterButton = detail.masterButton();
	}

	@Test
	public void navigation() throws IOException {
		masterView.verify().eventually().isPresent();
		masterView.verify().eventually().isVisible();
		masterView.whenPresent().flash();
		detailLabel.verify().eventually().isPresent();
		detailLabel.verify().eventually().isVisible();
		detailLabel.whenPresent().flash();

		detailLabel.whenPresent().touch();

		detailView.verify().eventually().isPresent();
		detailView.verify().eventually().isVisible();
		detailView.whenPresent().flash();
		masterButton.verify().eventually().isPresent();
		masterButton.verify().eventually().isVisible();
		masterButton.whenPresent().flash();

		masterButton.whenPresent().touch();

		masterView.verify().eventually().isPresent();
		masterView.verify().eventually().isVisible();
		masterView.whenPresent().flash();
		detailLabel.verify().eventually().isPresent();
		detailLabel.verify().eventually().isVisible();
		detailLabel.whenPresent().flash();
	}
}

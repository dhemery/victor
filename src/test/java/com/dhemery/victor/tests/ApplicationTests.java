package com.dhemery.victor.tests;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.victor.elements.Element;
import com.dhemery.victor.fixtures.DetailDisplay;
import com.dhemery.victor.fixtures.ExampleApplication;
import com.dhemery.victor.fixtures.MasterDisplay;
import com.dhemery.victor.test.VictorTest;

public class ApplicationTests extends VictorTest {
	private ExampleApplication application;

	@Before
	public void setUp() {
		application = new ExampleApplication(frank());
	}

	@Test
	public void navigation() {
		MasterDisplay master = application.masterDisplay();
		DetailDisplay detail = application.detailDisplay();
		Element masterView = master.masterView();
		Element detailLabel = master.detailLabel();
		Element detailView = detail.detailView();
		Element masterButton = detail.masterButton();

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

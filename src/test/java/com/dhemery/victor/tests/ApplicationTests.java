package com.dhemery.victor.tests;

import static com.dhemery.victor.view.Visible.visible;
import static org.hamcrest.Matchers.is;

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
		when(masterView, is(visible())).flash();
		when(detailLabel, is(visible())).flash();

		detailLabel.touch();

		assertThat(detailView, eventually(), is(visible()));
		detailView.flash();
		assertThat(masterButton, eventually(), is(visible()));
		masterButton.flash();

		masterButton.touch();

		assertThat(masterView, eventually(), is(visible()));
		assertThat(detailLabel, eventually(), is(visible()));
		masterView.flash();
		detailLabel.flash();
	}
}

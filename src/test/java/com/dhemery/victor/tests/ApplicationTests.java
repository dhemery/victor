package com.dhemery.victor.tests;

import static com.dhemery.victor.view.ViewExtensions.*;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.test.VictorTest;
import com.dhemery.victor.test.fixtures.DetailDisplay;
import com.dhemery.victor.test.fixtures.MasterDisplay;

public class ApplicationTests extends VictorTest {
    private ViewDriver masterView;
	private ViewDriver detailLabel;
	private ViewDriver detailView;
	private ViewDriver masterButton;

	@Before
	public void setUp() {
        MasterDisplay master = new MasterDisplay(application());
        DetailDisplay detail = new DetailDisplay(application());
		masterView = master.masterView();
		detailLabel = master.detailLabel();
		detailView = detail.detailView();
		masterButton = detail.masterButton();
	}

	@Test
	public void navigation() throws IOException {
		when(masterView, isVisible(), flash());
		when(detailLabel, isVisible(), flash());
		detailLabel.call(touch());

		when(detailView, eventually(), isVisible(), flash());
		assertThat(masterButton, eventually(), isVisible());
		masterButton.call(flash());

		masterButton.call(touch());

		assertThat(masterView, eventually(), isVisible());
		assertThat(detailLabel, eventually(), isVisible());
		masterView.call(flash());
		detailLabel.call(flash());
	}
}

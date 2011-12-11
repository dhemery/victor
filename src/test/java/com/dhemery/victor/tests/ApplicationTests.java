package com.dhemery.victor.tests;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.poller.PollTimeoutException;
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
	public void navigation() throws IOException, PollTimeoutException {
		assertThat(masterView).eventually().isPresent();
		assertThat(masterView).eventually().isVisible();
		when(masterView).isPresent().flash();
		assertThat(detailLabel).eventually().isPresent();
		assertThat(detailLabel).eventually().isVisible();
		when(detailLabel).isPresent().flash();

		when(detailLabel).isPresent().touch();

		assertThat(detailView).eventually().isPresent();
		assertThat(detailView).eventually().isVisible();
		when(detailView).isPresent().flash();
		assertThat(masterButton).eventually().isPresent();
		assertThat(masterButton).eventually().isVisible();
		when(masterButton).isPresent().flash();

		when(masterButton).isPresent().touch();

		assertThat(masterView).eventually().isPresent();
		assertThat(masterView).eventually().isVisible();
		when(masterView).isPresent().flash();
		assertThat(detailLabel).eventually().isPresent();
		assertThat(detailLabel).eventually().isVisible();
		when(detailLabel).isPresent().flash();
	}
}

package com.dhemery.victor.tests;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.poller.PollTimeoutException;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.test.VictorTest;
import com.dhemery.victor.test.fixtures.DetailDisplay;
import com.dhemery.victor.test.fixtures.MasterDisplay;
import static com.dhemery.victor.view.Present.present;
import static com.dhemery.victor.view.Visible.visible;

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
		assertThat(masterView).eventually().is(present());
		assertThat(masterView).eventually().is(visible());
		when(masterView).is(present()).flash();
		assertThat(detailLabel).eventually().is(present());
		assertThat(detailLabel).eventually().is(visible());
		when(detailLabel).is(present()).flash();

		waitUntil(detailLabel).is(present());

		when(detailLabel).is(present()).touch();

		assertThat(detailView).eventually().is(present());
		assertThat(detailView).eventually().is(visible());
		when(detailView).is(present()).flash();
		assertThat(masterButton).eventually().is(present());
		assertThat(masterButton).eventually().is(visible());
		when(masterButton).is(present()).flash();

		when(masterButton).is(present()).touch();

		assertThat(masterView).eventually().is(present());
		assertThat(masterView).eventually().is(visible());
		when(masterView).is(present()).flash();
		assertThat(detailLabel).eventually().is(present());
		assertThat(detailLabel).eventually().is(visible());
		when(detailLabel).is(present()).flash();
	}
}

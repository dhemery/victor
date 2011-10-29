package com.dhemery.victor.tests;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.poller.PollTimeoutException;
import com.dhemery.poller.RequiredConditionException;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.test.VictorTest;

import static com.dhemery.victor.ApplicationDriver.Orientation.*;

public class PhoneTests extends VictorTest {
	private ApplicationDriver application;
	private PhoneDriver phone;

	@Before
	public void setUp() {
		application = application();
		phone = phone();		
	}

	@Test
	public void orientation() throws InterruptedException, RequiredConditionException, PollTimeoutException, IOException {
		application.verify().hasOrientation(PORTRAIT);

		phone.rotateLeft();
		application.verify().eventually().hasOrientation(LANDSCAPE);

		phone.rotateRight();
		application.verify().eventually().hasOrientation(PORTRAIT);

		phone.rotateRight();
		application.verify().eventually().hasOrientation(LANDSCAPE);

		phone.rotateRight();
		application.verify().eventually().hasOrientation(PORTRAIT);
	}
}

package com.dhemery.victor.tests;

import static com.dhemery.polling.Has.has;
import static com.dhemery.victor.ApplicationDriver.Orientation.LANDSCAPE;
import static com.dhemery.victor.ApplicationDriver.Orientation.PORTRAIT;
import static com.dhemery.victor.application.OrientationQuery.orientation;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.test.VictorTest;

public class PhoneTests extends VictorTest {
	private ApplicationDriver application;
	private PhoneDriver phone;

	@Before
	public void setUp() {
		application = application();
		phone = phone();		
	}

	@Test
	public void orientationTests() throws InterruptedException, IOException {
		// The following assertions all assert the same condition, with different styles.
		assertThat(application, has(orientation(), equalTo(PORTRAIT)));

		// The following waits all wait for the same condition, with different styles.
		waitUntil(application, has(orientation(), equalTo(PORTRAIT)));

		phone.rotateLeft();
		assertThat(application, eventually(), has(orientation(), equalTo(LANDSCAPE)));

		phone.rotateRight();
		assertThat(application, eventually(), has(orientation(), equalTo(PORTRAIT)));

		phone.rotateRight();
		assertThat(application, eventually(), has(orientation(), equalTo(LANDSCAPE)));

		phone.rotateRight();
		assertThat(application, eventually(), has(orientation(), equalTo(PORTRAIT)));
	}
	}

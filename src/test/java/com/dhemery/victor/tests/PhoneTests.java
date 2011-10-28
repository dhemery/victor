package com.dhemery.victor.tests;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.victor.Application;
import com.dhemery.victor.Phone;
import com.dhemery.victor.test.VictorTest;

import static com.dhemery.victor.Application.Orientation.*;

public class PhoneTests extends VictorTest {
	private Application application;
	private Phone phone;

	@Before
	public void setUp() {
		application = application();
		phone = phone();		
	}

	@Test
	public void orientation() throws InterruptedException {
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

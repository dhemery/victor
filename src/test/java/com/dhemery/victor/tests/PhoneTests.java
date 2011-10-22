package com.dhemery.victor.tests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.victor.phone.Phone;
import com.dhemery.victor.test.VictorTest;

public class PhoneTests extends VictorTest {
	private Phone phone;
	
	@Before
	public void setUp() {
		phone = new Phone(frank());
	}

	@Test
	public void orientation() {
		assertThat(phone.orientation(), equalTo(Phone.Orientation.PORTRAIT));
	}
}

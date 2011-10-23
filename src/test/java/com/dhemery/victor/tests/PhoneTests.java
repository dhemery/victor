package com.dhemery.victor.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.dhemery.victor.phone.Phone;
import com.dhemery.victor.test.VictorTest;

public class PhoneTests extends VictorTest {
	@Test
	public void orientation() throws InterruptedException {
		assertThat(phone().orientation(), equalTo(Phone.Orientation.PORTRAIT));
		phone().rotateLeft();
		Thread.sleep(2000);
		assertThat(phone().orientation(), equalTo(Phone.Orientation.LANDSCAPE));
		phone().rotateRight();
		Thread.sleep(2000);
		assertThat(phone().orientation(), equalTo(Phone.Orientation.PORTRAIT));
		phone().rotateRight();
		Thread.sleep(2000);
		assertThat(phone().orientation(), equalTo(Phone.Orientation.LANDSCAPE));
		phone().rotateRight();
		Thread.sleep(2000);
		assertThat(phone().orientation(), equalTo(Phone.Orientation.PORTRAIT));
	}
}

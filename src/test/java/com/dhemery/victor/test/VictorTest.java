package com.dhemery.victor.test;

import java.io.IOException;

import org.hamcrest.SelfDescribing;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.dhemery.pollable.AssertThat;
import com.dhemery.pollable.WaitUntil;
import com.dhemery.pollable.When;
import com.dhemery.poller.PollTimeoutException;
import com.dhemery.poller.Timer;
import com.dhemery.properties.RequiredProperties;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.PhoneDriver;

public class VictorTest {
	private static ApplicationDriver application;
	private static PhoneDriver phone;
	private static Timer timer;

	@BeforeClass
	public static void launchApp() throws IOException, PollTimeoutException {
		RequiredProperties configuration = new RequiredProperties("default.properties", "my.properties");
		Launcher launcher = new Launcher(configuration);
		launcher.launch();
		timer = launcher.timer();
		phone = launcher.phone();
		application = launcher.application();
		verifyAccessibilityIsEnabled();
	}

	private static void verifyAccessibilityIsEnabled() throws IOException {
		if(!phone.isEnabledForAccessibility()) {
			throw new RuntimeException("Please enable accessibility in the Phone/Simulator settings: Settings > General > Accessibility.");
		}
	}

	@AfterClass
	public static void shutDownPhone() throws IOException, InterruptedException {
		phone.shutDown();
	}

	public ApplicationDriver application() { return application; }
	public PhoneDriver phone() { return phone; }

	public <S extends SelfDescribing> AssertThat<S> assertThat(S subject) {
		return new AssertThat<S>(subject, timer);
	}

	public <S extends SelfDescribing> When<S> when(S subject) {
		return new When<S>(subject, timer);
	}
	
	public <S extends SelfDescribing> WaitUntil<S> waitUntil(S subject) {
		return new WaitUntil<S>(subject, timer);
	}
}

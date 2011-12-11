package com.dhemery.victor.test;

import java.io.IOException;

import org.hamcrest.SelfDescribing;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.dhemery.assertions.PollableAssertions;
import com.dhemery.poller.Poll;
import com.dhemery.poller.PollTimeoutException;
import com.dhemery.poller.Subject;
import com.dhemery.preconditions.PolledPreconditions;
import com.dhemery.properties.RequiredProperties;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.PhoneDriver;

public class VictorTest {
	private static ApplicationDriver application;
	private static PhoneDriver phone;
	private static Poll poll;

	@BeforeClass
	public static void launchApp() throws IOException, PollTimeoutException {
		RequiredProperties configuration = new RequiredProperties("default.properties", "my.properties");
		Launcher launcher = new Launcher(configuration);
		launcher.launch();
		poll = launcher.poll();
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

	public <T extends SelfDescribing> PollableAssertions<T> assertThat(T subject) {
		return new PollableAssertions<T>(subject, poll);
	}

	public <T extends SelfDescribing> PolledPreconditions<T> when(T subject) {
		return new PolledPreconditions<T>(subject, poll);
	}
	
	public <T extends SelfDescribing> Subject<T> waitUntil(T subject) {
		return poll.until(subject);
	}
}

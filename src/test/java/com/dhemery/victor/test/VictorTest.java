package com.dhemery.victor.test;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.dhemery.poller.Poll;
import com.dhemery.poller.PollTimeoutException;
import com.dhemery.properties.RequiredProperties;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.application.ApplicationAssertion;
import com.dhemery.victor.application.PolledApplicationConditions;
import com.dhemery.victor.view.PolledViewConditions;
import com.dhemery.victor.view.ViewAssertion;

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

	public ViewAssertion assertThat(ViewDriver view) {
		return new ViewAssertion(view, poll);
	}

	public PolledViewConditions when(ViewDriver view) {
		return new PolledViewConditions(view, poll);
	}

	public ApplicationAssertion assertThat(ApplicationDriver application) {
		return new ApplicationAssertion(application, poll);
	}
	
	public PolledApplicationConditions when(ApplicationDriver application) {
		return new PolledApplicationConditions(application, poll);
	}
}

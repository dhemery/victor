package com.dhemery.victor.test;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.dhemery.poller.PollTimeoutException;
import com.dhemery.properties.RequiredProperties;
import com.dhemery.sentences.Sentences;
import com.dhemery.sentences.internal.PollableSentence;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.PhoneDriver;

public class VictorTest {
	private static ApplicationDriver application;
	private static PhoneDriver phone;
	private static Sentences sentences;

	@BeforeClass
	public static void launchApp() throws IOException, PollTimeoutException {
		RequiredProperties configuration = new RequiredProperties("default.properties", "my.properties");
		Launcher launcher = new Launcher(configuration);
		launcher.launch();
		sentences = launcher.sentences();
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

	public <S> PollableSentence<S,Void> assertThat(S subject) {
		return sentences.assertThat(subject);
	}

	public <S> PollableSentence<S,S> when(S subject) {
		return sentences.when(subject);
	}
	
	public <S> PollableSentence<S,Void> waitUntil(S subject) {
		return sentences.assertThat(subject);
	}
	
	public <S> PollableSentence<S,Boolean> the(S subject) {
		return sentences.valueOf(subject);
	}
}

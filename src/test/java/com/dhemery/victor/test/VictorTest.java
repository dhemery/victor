package com.dhemery.victor.test;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.dhemery.polling.PollTimeoutError;
import com.dhemery.properties.RequiredProperties;
import com.dhemery.sentences.PollableSentence;
import com.dhemery.sentences.Sentences;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.simulator.Simulator;

public class VictorTest {
	private static ApplicationDriver application;
	private static PhoneDriver phone;
	private static Sentences sentences;
	private static Simulator simulator;

	@BeforeClass
	public static void launchApp() throws IOException, PollTimeoutError {
		RequiredProperties configuration = new RequiredProperties("default.properties", "my.properties");
		Launcher launcher = new Launcher(configuration);
		launcher.launch();
		sentences = launcher.sentences();
		phone = launcher.phone();
		application = launcher.application();
		simulator = launcher.simulator();
	}

	@AfterClass
	public static void shutDownSimulator() throws IOException, InterruptedException {
		simulator.shutDown();
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
		return sentences.waitUntil(subject);
	}
	
	public <S> PollableSentence<S,Boolean> the(S subject) {
		return sentences.the(subject);
	}
}

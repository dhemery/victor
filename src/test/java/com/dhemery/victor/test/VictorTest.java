package com.dhemery.victor.test;

import java.io.IOException;

import com.dhemery.polling.PollTimeoutException;
import com.dhemery.victor.Action;
import com.dhemery.victor.ViewDriver;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.dhemery.polling.MatcherPoll;
import com.dhemery.polling.PollTimer;
import com.dhemery.properties.RequiredProperties;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.simulator.Simulator;

public class VictorTest {
	private static ApplicationDriver application;
	private static PhoneDriver phone;
	private static Simulator simulator;
	private static PollTimer timer;

	@BeforeClass
	public static void launchApp() throws IOException, PollTimeoutException {
		RequiredProperties configuration = new RequiredProperties("default.properties", "my.properties");
		Launcher launcher = new Launcher(configuration);
		launcher.launch();
		phone = launcher.phone();
		application = launcher.application();
		simulator = launcher.simulator();
		timer = launcher.timer();
	}

	@AfterClass
	public static void shutDownSimulator() throws IOException, InterruptedException {
        if(simulator != null) simulator.shutDown();
	}


	public ApplicationDriver application() { return application; }
	public PhoneDriver phone() { return phone; }
	
	public PollTimer eventually() {
		return timer;
	}
	
	public static <S> void assertThat(S subject, Matcher<? super S> matcher) {
		MatcherAssert.assertThat(subject, matcher);
	}

	public <S> void assertThat(S subject, PollTimer timer, Matcher<? super S> matcher) {
		new MatcherPoll<S>(subject, matcher, eventually()).run();
	}

	public <S> void waitUntil(S subject, Matcher<? super S> matcher) {
		assertThat(subject, eventually(), matcher);
	}

    public <S> void when(S subject, Matcher<? super S> matcher, Action<? super S> action) {
        when(subject, eventually(), matcher, action);
    }

    public <S> void when(S subject, PollTimer timer, Matcher<? super S> matcher, Action<? super S> action) {
        assertThat(subject, timer, matcher);
        action.executeOn(subject);
    }

    public void view(ViewDriver view, Action<ViewDriver> action) {
        action.executeOn(view);
    }
}

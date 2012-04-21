package com.dhemery.victor.test;

import com.dhemery.polling.Action;
import com.dhemery.polling.PollTimeoutException;
import com.dhemery.polling.PollTimer;
import com.dhemery.polling.PollableExpressions;
import com.dhemery.properties.RequiredProperties;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.simulator.Simulator;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;

public class VictorTest extends PollableExpressions {
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
	
	@Override
    public PollTimer eventually() {
		return timer;
	}
	
    public void view(ViewDriver view, Action<ViewDriver> action) {
        action.executeOn(view);
    }
}

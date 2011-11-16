package com.dhemery.victor.test.tools;


import java.io.IOException;

import com.dhemery.poller.PollTimeoutException;
import com.dhemery.properties.RequiredProperties;
import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.test.Launcher;

public class VerifyAccessibilityIsEnabled {
	public static void main(String[] args) throws IOException, PollTimeoutException, InterruptedException {
		RequiredProperties configuration = new RequiredProperties("default.properties", "my.properties");
		PhoneDriver phone = phoneLaunchedWith(configuration);
		try {
			System.out.format("Enabled for accessibility: %s%n", phone.isEnabledForAccessibility());
		} finally {
			phone.shutDown();
		}
	}

	private static PhoneDriver phoneLaunchedWith(RequiredProperties configuration) throws IOException, PollTimeoutException {
		Launcher launcher = new Launcher(configuration);
		launcher.launch();
		return launcher.phone();
	}
}

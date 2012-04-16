package com.dhemery.victor.simulator.remote;

import com.dhemery.victor.http.HttpRequest;

public class LaunchApplicationRequest extends HttpRequest {
	public static final String VERB = "launchApplication";

	public LaunchApplicationRequest(String applicationPath) {
		super(VERB, new LaunchApplicationCommand(applicationPath));
	}
}

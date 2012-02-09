package com.dhemery.victor.simulator;

import com.dhemery.victor.http.HttpRequest;

public class RemoteLaunchApplicationCommand extends HttpRequest {
	public static final String VERB = "launchApplication";

	public RemoteLaunchApplicationCommand(String applicationPath) {
		super(VERB, new RemoteLaunchApplicationCommandBody(applicationPath));
	}
}

package com.dhemery.victor.simulator;

import com.dhemery.victor.http.HttpRequest;

public class RemoteLaunchApplicationCommand extends HttpRequest {
	public RemoteLaunchApplicationCommand(String applicationPath) {
		super("launchApplication", new RemoteLaunchApplicationCommandBody(applicationPath));
	}
}

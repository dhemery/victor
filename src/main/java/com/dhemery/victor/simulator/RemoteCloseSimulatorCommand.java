package com.dhemery.victor.simulator;

import com.dhemery.victor.http.HttpRequest;

public class RemoteCloseSimulatorCommand extends HttpRequest {
	public static final String VERB = "closeSimulator";

	public RemoteCloseSimulatorCommand() {
		super(VERB);
	}
}

package com.dhemery.victor.simulator.remote;

import com.dhemery.victor.http.HttpRequest;

public class CloseSimulatorRequest extends HttpRequest {
	public static final String VERB = "closeSimulator";

	public CloseSimulatorRequest() {
		super(VERB);
	}
}

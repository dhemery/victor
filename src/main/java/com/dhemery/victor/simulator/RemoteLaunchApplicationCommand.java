package com.dhemery.victor.simulator;

import com.dhemery.victor.http.HttpPostBody;
import com.dhemery.victor.http.HttpRequest;

public class RemoteLaunchApplicationCommand extends HttpRequest {
	public static final String VERB = "launchApplication";

	public RemoteLaunchApplicationCommand(String applicationPath) {
		super(VERB, new Body(applicationPath));
	}

	public static class Body extends HttpPostBody {
		public final String applicationPath;

		public Body(String applicationPath) {
			this.applicationPath = applicationPath;
		}

		@Override
		public String toString() {
			return String.format("[applicationPath:%s]", applicationPath);
		}
	}
}

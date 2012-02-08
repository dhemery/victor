package com.dhemery.victor.simulator;

import com.dhemery.victor.http.HttpPostBody;

public class RemoteLaunchApplicationCommandBody extends HttpPostBody {
	public final String applicationPath;

	public RemoteLaunchApplicationCommandBody(String applicationPath) {
		this.applicationPath = applicationPath;
	}

	@Override
	public String toString() {
		return String.format("[applicationPath:%s]", applicationPath);
	}
}

package com.dhemery.victor.simulator.remote;

import com.dhemery.victor.http.HttpPostBody;

public class LaunchApplicationMessage extends HttpPostBody {
	public final String applicationPath;

	public LaunchApplicationMessage(String applicationPath) {
		this.applicationPath = applicationPath;
	}

	@Override
	public String toString() {
		return String.format("[applicationPath:%s]", applicationPath);
	}
}

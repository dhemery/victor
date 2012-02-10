package com.dhemery.victor.simulator.remote;

import com.dhemery.victor.http.HttpPostBody;

public class LaunchApplicationCommand extends HttpPostBody {
	public final String applicationPath;
	public final String deviceType;

	public LaunchApplicationCommand(String applicationPath, String deviceType) {
		this.applicationPath = applicationPath;
		this.deviceType = deviceType;
	}

	@Override
	public String toString() {
		return String.format("[applicationPath:%s][deviceType:%s]", applicationPath, deviceType);
	}
}

package com.dhemery.victor.simulator.remote;

import com.dhemery.victor.http.HttpPostBody;

public class LaunchApplicationCommand extends HttpPostBody {
	public final String applicationPath;
	public final String deviceType;
	public final String sdkRoot;

	public LaunchApplicationCommand(String applicationPath, String deviceType, String sdkRoot) {
		this.applicationPath = applicationPath;
		this.deviceType = deviceType;
		this.sdkRoot = sdkRoot;
	}

	@Override
	public String toString() {
		return String.format("[applicationPath:%s][deviceType:%s][sdkRoot:%s]", applicationPath, deviceType, sdkRoot);
	}
}

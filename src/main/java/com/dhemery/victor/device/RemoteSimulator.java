package com.dhemery.victor.device;

import com.dhemery.victor.http.HttpRequest;
import com.dhemery.victor.device.remote.TouchMenuItemRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteSimulator implements Simulator {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final String simulatorUrl;

    public RemoteSimulator(String simulatorUrl) {
		this.simulatorUrl = simulatorUrl;
        log.debug("Using remote simulator at {}", simulatorUrl);
	}

	private void perform(HttpRequest operation) {
		operation.sendTo(simulatorUrl);
	}

	@Override
	public void touchMenuItem(String menuName, String menuItemName) {
		perform(new TouchMenuItemRequest(menuName, menuItemName));
	}
}

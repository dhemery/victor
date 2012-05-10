package com.dhemery.victor.device;

import com.dhemery.victor.device.remote.TouchMenuItemRequest;
import com.dhemery.victor.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteSimulatorAgent implements SimulatorAgent {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String simulatorUrl;

    public RemoteSimulatorAgent(String simulatorUrl) {
        this.simulatorUrl = simulatorUrl;
        log.debug("Using remote simulator at {}", simulatorUrl);
    }

    private void perform(HttpRequest operation) {
        operation.sendTo(simulatorUrl);
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void touchMenuItem(String menuName, String menuItemName) {
        perform(new TouchMenuItemRequest(menuName, menuItemName));
    }
}

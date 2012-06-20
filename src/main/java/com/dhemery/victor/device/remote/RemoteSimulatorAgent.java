package com.dhemery.victor.device.remote;

import com.dhemery.victor.VictorEntryPoint;
import com.dhemery.victor.device.SimulatorAgent;
import com.dhemery.victor.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interacts with a Simulator via a simulator server.
 */

@SuppressWarnings("UnusedDeclaration")
public class RemoteSimulatorAgent implements SimulatorAgent {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String simulatorUrl;

    @VictorEntryPoint
    public RemoteSimulatorAgent(String simulatorUrl) {
        this.simulatorUrl = simulatorUrl;
        log.debug("Using remote simulator at {}", simulatorUrl);
    }

    private void perform(HttpRequest operation) {
        operation.sendTo(simulatorUrl);
    }

    @Override
    public void start() {
        log.debug("Start is not yet implemented.");
    }

    @Override
    public void stop() {
        log.debug("Stop is not yet implemented.");
    }

    @Override
    public void touchMenuItem(String menuName, String menuItemName) {
        perform(new TouchMenuItemRequest(menuName, menuItemName));
    }
}

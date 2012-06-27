package com.dhemery.victor.device.remote;

import com.dhemery.victor.http.HttpRequest;
import com.dhemery.victor.os.MetaKey;
import com.dhemery.victor.os.OsxApplication;
import com.dhemery.victor.os.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interacts with a Simulator via a simulator server.
 */

public class RemoteSimulator implements OsxApplication, Service {
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
    public void start() {
        log.debug("Start is not yet implemented.");
    }

    @Override
    public void stop() {
        log.debug("Stop is not yet implemented.");
    }

    @Override
    public void touchMenuItem(String menu, String item) {
    }

    @Override
    public void touchMenuItem(String menu, String submenu, String item) {
    }

    @Override
    public void typeKey(char key, MetaKey metaKey) {
    }
}
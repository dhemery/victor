package com.dhemery.victor.device.remote;

import com.dhemery.victor.http.HttpRequest;
import com.dhemery.victor.os.MetaKey;
import com.dhemery.victor.os.OsxApplication;
import com.dhemery.victor.os.Service;

/**
 * Interacts with a Simulator via a simulator server.
 */

public class RemoteSimulator implements OsxApplication, Service {
    private final String simulatorUrl;

    public RemoteSimulator(String simulatorUrl) {
        this.simulatorUrl = simulatorUrl;
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
    public void touchMenuItem(String menu, String item) {
    }

    @Override
    public void touchMenuItem(String menu, String submenu, String item) {
    }

    @Override
    public void typeKey(char key, MetaKey metaKey) {
    }
}
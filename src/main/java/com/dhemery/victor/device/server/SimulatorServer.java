package com.dhemery.victor.device.server;

import com.dhemery.victor.device.local.SimulatorApplication;
import com.dhemery.victor.device.remote.CloseSimulatorRequest;
import com.dhemery.victor.device.remote.LaunchApplicationRequest;
import com.dhemery.victor.device.remote.TouchMenuItemRequest;
import com.dhemery.victor.os.Shell;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimulatorServer {
    private static final int PORT = 4567;
    private static final InetSocketAddress ADDRESS = new InetSocketAddress(PORT);

    public static void main(String... args) {
        try {
            new SimulatorServer().run();
        } catch (IOException ignored) {
        }
    }

    private final HttpServer server;

    public SimulatorServer() throws IOException {
        SimulatorApplication simulator = new SimulatorApplication(new Shell());
        server = HttpServer.create();
        server.bind(ADDRESS, PORT);
        server.createContext(String.format("/%s", LaunchApplicationRequest.VERB), new LaunchApplicationHandler(simulator));
        server.createContext(String.format("/%s", CloseSimulatorRequest.VERB), new CloseSimulatorHandler(simulator));
        server.createContext(String.format("/%s", TouchMenuItemRequest.VERB), new TouchMenuItemHandler(simulator));
    }

    private void run() {
        server.start();
    }
}

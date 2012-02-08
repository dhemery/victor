package com.dhemery.victor.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.victor.simulator.VictorOwnedSimulator;
import com.sun.net.httpserver.HttpServer;

public class SimulatorServer {
	private static final int PORT = 4567;
	private static final Logger log = LoggerFactory.getLogger(SimulatorServer.class);
	private static final InetSocketAddress ADDRESS = new InetSocketAddress(PORT);
	private static final String SIMULATOR_PATH = "/Developer/Platforms/iPhoneSimulator.platform/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator";

	public static void main(String[] args) {
		try {
			new SimulatorServer().run();
		} catch(Throwable e) {
			log.debug("SimulatorServer aborted with {}", e);
		}
	}
	private final HttpServer server;
	private final VictorOwnedSimulator simulator;

	public SimulatorServer() throws IOException {
		simulator = new VictorOwnedSimulator(SIMULATOR_PATH);
		server = HttpServer.create();
		server.bind(ADDRESS, PORT);
		server.createContext("/launchApplication", new LaunchApplicationHandler(simulator));
		server.createContext("/closeSimulator", new CloseSimulatorHandler(simulator));
		server.createContext("/touchMenuItem", new TouchMenuItemHandler(simulator));
	}

	private void run() {
		server.start();
		log.info("SimulatorServer on port {} launched", PORT);
	}
}

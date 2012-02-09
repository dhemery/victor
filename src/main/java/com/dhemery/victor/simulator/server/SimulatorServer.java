package com.dhemery.victor.simulator.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.victor.simulator.local.LocalSimulator;
import com.dhemery.victor.simulator.remote.RemoteCloseSimulatorCommand;
import com.dhemery.victor.simulator.remote.RemoteLaunchApplicationCommand;
import com.dhemery.victor.simulator.remote.RemoteTouchMenuItemCommand;
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
	private final LocalSimulator simulator;

	public SimulatorServer() throws IOException {
		simulator = new LocalSimulator(SIMULATOR_PATH);
		server = HttpServer.create();
		server.bind(ADDRESS, PORT);
		server.createContext(String.format("/%s", RemoteLaunchApplicationCommand.VERB), new LaunchApplicationHandler(simulator));
		server.createContext(String.format("/%s", RemoteCloseSimulatorCommand.VERB), new CloseSimulatorHandler(simulator));
		server.createContext(String.format("/%s", RemoteTouchMenuItemCommand.VERB), new TouchMenuItemHandler(simulator));
	}

	private void run() {
		server.start();
		log.info("SimulatorServer on port {} launched", PORT);
	}
}

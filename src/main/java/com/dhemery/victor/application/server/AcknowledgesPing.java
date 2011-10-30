package com.dhemery.victor.application.server;

import com.dhemery.poller.Condition;

/**
 * A condition that is satisfied when the application server responds to a "ping" request.
 * @author Dale Emery
 *
 */
public class AcknowledgesPing extends Condition {
	private final ApplicationServer server;

	public AcknowledgesPing(ApplicationServer server) {
		this.server = server;
	}

	@Override
	public String describe() {
		return "Application server responds to ping";
	}

	@Override
	public boolean isSatisfied() {
		return server.ping();
	}
}
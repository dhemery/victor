package com.dhemery.victor.frank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.poller.Condition;

/**
 * A condition that is satisfied when the Frank server acknowledges a "ping" request.
 * @author Dale Emery
 *
 */
class FrankServerAcknowledgesPing extends Condition {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final String serverPath;

	public FrankServerAcknowledgesPing(String serverPath) {
		this.serverPath = serverPath;
	}

	@Override
	public String describe() {
		return "Frank server acknowledges ping";
	}

	@Override
	public boolean isSatisfied() {
		return ping();
	}

	private boolean ping() {
		try {
			new PingRequest().sendTo(serverPath);
		} catch (Throwable e) {
			log.debug("Ping failed: {}", e.getMessage());
			return false;
		}
		log.debug("Ping succeeded");
		return true;
	}
}
package com.dhemery.victor.frank;

import java.io.IOException;
import java.net.ProtocolException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.poller.Condition;
import com.dhemery.victor.http.Request;

final class FrankServerAcknowledgesPing implements Condition {
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
			new Request("").sendTo(serverPath);
		} catch (ProtocolException e) {
			log.debug("Ping got Protocol exception: {}", e.getMessage());
			return false;
		} catch (IOException e) {
			log.debug("Ping got IO Exception: {}", e.getMessage());
			return false;
		}
		log.debug("Ping succeeded");
		return true;
	}
}
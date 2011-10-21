package com.dhemery.victor.driver;

import java.io.IOException;
import java.net.ProtocolException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.poller.Condition;
import com.dhemery.victor.driver.io.PingRequest;

final class ApplicationServerResponds implements Condition {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final String serverPath;

	public ApplicationServerResponds(String serverPath) {
		this.serverPath = serverPath;
	}

	@Override
	public String describe() {
		return "application server responds";
	}

	@Override
	public boolean isSatisfied() {
		return ping();
	}
	
	boolean ping() {
		try {
			new PingRequest().sendTo(serverPath);
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
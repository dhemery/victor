package com.dhemery.victor.frank;

import com.dhemery.victor.IosApplication;
import com.dhemery.victor.frank.messages.Message;
import com.dhemery.victor.frank.messages.MessageException;
import com.dhemery.victor.frank.messages.MessageResponse;
import com.dhemery.victor.frank.messages.OrientationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interacts with an application through an {@code IosApplicationAgent}.
 * @author Dale Emery
 */
public class FrankIosApplication implements IosApplication {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final IosApplicationAgent agent;

	/**
	 * @param agent an agent that can interact with this application.
	 */
	public FrankIosApplication(IosApplicationAgent agent) {
		this.agent = agent;
	}

    @Override
    public String sendMessage(String name, String... arguments) {
        Message message = new Message(name, arguments);
        log.debug("Send: application delegate {}", message);
        MessageResponse response = agent.sendApplicationMessage(message);
        if(!response.succeeded) throw new MessageException(this, message, response);
        log.debug("Application delegate message {} returned {}", message, response.results);
        return response.results.get(0);
    }

    @Override
    public Orientation orientation() {
        OrientationResponse response = agent.orientation();
        String orientationName = response.orientation.toUpperCase();
        return Orientation.valueOf(orientationName);
    }

	@Override
	public String toString() {
		return "the application";
	}
}

package com.dhemery.victor.application;

import com.dhemery.victor.IosApplication;
import com.dhemery.victor.message.Message;
import com.dhemery.victor.message.MessageException;
import com.dhemery.victor.message.MessageResponse;
import org.hamcrest.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An application driver that interacts with an application through a Frank server.
 * @author Dale Emery
 */
public class AgentedIosApplication implements IosApplication {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final IosApplicationAgent agent;

	/**
	 * @param agent a Frank client that can interact with this application.
	 */
	public AgentedIosApplication(IosApplicationAgent agent) {
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
	public void describeTo(Description description) {
		description.appendText(toString());
	}

	@Override
	public String toString() {
		return "the application";
	}
}

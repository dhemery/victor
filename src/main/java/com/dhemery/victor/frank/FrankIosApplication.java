package com.dhemery.victor.frank;

import com.dhemery.victor.By;
import com.dhemery.victor.IosApplication;
import com.dhemery.victor.IosApplicationOrientation;
import com.dhemery.victor.IosView;
import com.dhemery.victor.frank.frankly.ApplicationMessageRequest;
import com.dhemery.victor.frank.frankly.ApplicationOrientationRequest;
import com.dhemery.victor.frank.messages.Message;
import com.dhemery.victor.frank.messages.MessageException;
import com.dhemery.victor.frank.messages.MessageResponse;
import com.dhemery.victor.frank.messages.OrientationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interacts with an application through a {@code FrankApplicationAgent}.
 *
 * @author Dale Emery
 */
public class FrankIosApplication implements IosApplication {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final FrankAgent agent;

    /**
     * @param agent an agent that can interact with this application.
     */
    public FrankIosApplication(FrankAgent agent) {
        this.agent = agent;
    }

    @Override
    public String sendMessage(String name, Object... arguments) {
        Message message = new Message(name, arguments);
        log.debug("Send: application delegate {}", message);
        MessageResponse response = agent.sendMessageRequest(new ApplicationMessageRequest(message));
        if (response.failed()) throw new MessageException(this, message, response);
        log.debug("Application delegate message {} returned {}", message, response.results());
        return response.results().get(0);
    }

    @Override
    public Boolean isRunning() {
        return agent.isRunning();
    }

    @Override
    public IosApplicationOrientation orientation() {
        OrientationResponse response = agent.sendRequest(new ApplicationOrientationRequest(), OrientationResponse.class);
        String orientationName = response.orientation().toUpperCase();
        return IosApplicationOrientation.valueOf(orientationName);
    }

    @Override
    public IosView view(By query) {
        return agent.view(query);
    }

    @Override
    public String toString() {
        return "the application connected to " + agent;
    }
}

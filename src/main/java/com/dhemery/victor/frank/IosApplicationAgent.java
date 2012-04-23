package com.dhemery.victor.frank;

import com.dhemery.victor.frank.messages.Message;
import com.dhemery.victor.frank.messages.MessageResponse;
import com.dhemery.victor.frank.messages.OrientationResponse;

public interface IosApplicationAgent {
    /**
     * @return true if the agent is ready to communicate with the application, otherwise false.
     */
    boolean isReady();

    /**
     * Determines the current orientation (portrait or landscape)
     * of the application in which the Frank server is running.
     * @return a response that describes the application's orientation.
     */
    OrientationResponse orientation();

    /**
     * Send a message to the application's application delegate.
     * @param message the message to send.
     * @return the application delegate's response.
     */
    MessageResponse sendApplicationMessage(Message message);
}

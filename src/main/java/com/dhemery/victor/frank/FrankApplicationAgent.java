package com.dhemery.victor.frank;

import com.dhemery.victor.By;
import com.dhemery.victor.IosView;
import com.dhemery.victor.frank.messages.Message;
import com.dhemery.victor.frank.messages.MessageResponse;
import com.dhemery.victor.frank.messages.OrientationResponse;

public interface FrankApplicationAgent {
    /**
     * Determines the current orientation (portrait or landscape)
     * of the application in which the Frank server is running.
     *
     * @return a response that describes the application's orientation.
     */
    OrientationResponse orientation();

    /**
     * Send a message to the application delegate.
     *
     * @param message the message to send.
     * @return the application delegate's response.
     */
    MessageResponse sendApplicationMessage(Message message);

    /**
     * @return whether the agent is running.
     */
    Boolean isRunning();

    /**
     * @param query identifies a set of views.
     * @return a view driver that represents the identified views within this application.
     */

    IosView view(By query);

}

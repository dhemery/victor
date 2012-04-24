package com.dhemery.victor.frank;

import com.dhemery.victor.By;
import com.dhemery.victor.frank.messages.Message;
import com.dhemery.victor.frank.messages.MessageResponse;

public interface IosViewAgent {
    /**
     * @return true if the agent is ready to communicate with views.
     */
    boolean isReady();

    /**
     * Send a message to zero or more views and returns their answers.
     * @param query identifies the views that will receive the message.
     * @param message the message to send.
     * @return the results returned by the views that received the message.
     */
    MessageResponse sendViewMessage(By query, Message message);
}

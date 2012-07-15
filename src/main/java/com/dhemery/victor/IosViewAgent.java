package com.dhemery.victor;

import java.util.List;

/**
 * An agent that sends and receives messages on behalf of iOS views.
 */
public interface IosViewAgent {
    /**
     * Send a message to a set of views.
     * @param query a query that identifies a set of views.
     * @param name the name of the message to send (an Objective-C selectxor).
     * @param arguments arguments to send with the message.
     * @return The views' responses to the message.
     */
    List<String> sendMessage(By query, String name, Object... arguments);
}

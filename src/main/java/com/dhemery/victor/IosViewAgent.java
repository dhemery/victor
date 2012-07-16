package com.dhemery.victor;

import java.util.List;

/**
 * An agent that transmits messages and responses between Victor and iOS views.
 */
public interface IosViewAgent {
    /**
     * Send a message to the views identified by a query.
     * @param query a query that identifies a set of views.
     * @param name the name of the message to send (an Objective-C selector).
     * @param arguments arguments to send with the message.
     * @return A list of the views' responses to the message.
     */
    List<String> sendMessage(By query, String name, Object... arguments);
}

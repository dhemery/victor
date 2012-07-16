package com.dhemery.victor;

import java.util.List;

/**
 * A driver that interacts with a set of views in an iOS application.
 *
 * @author Dale Emery
 */
public interface IosView {
    /**
     * The query that identifies the views represented by this driver.
     * @return the query that identifies the views represented by this driver.
     */
    By query();

    /**
     * Send a message to each represented view.
     *
     * @param name      the name of the message to send (an Objective-C query).
     * @param arguments arguments to send with the message.
     * @return the list of values returned from the represented views.
     */
    List<String> sendMessage(String name, Object... arguments);
}

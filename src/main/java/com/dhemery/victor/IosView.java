package com.dhemery.victor;

import java.util.List;

/**
 * Represents a set of views in an iOS application.
 *
 * @author Dale Emery
 */
public interface IosView {
    /**
     * @return the query that identifies the views represented by this IosView.
     */
    @VictorEntryPoint
    By query();

    /**
     * Send a message with arguments to each view represented by this IosView.
     *
     * @param name      the name of the message to send (an Objective-C query).
     * @param arguments arguments to send with the message.
     * @return the value returned from each represented view.
     */
    @VictorEntryPoint
    List<String> sendMessage(String name, Object... arguments);
}

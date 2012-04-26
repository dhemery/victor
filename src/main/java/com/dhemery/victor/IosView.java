package com.dhemery.victor;

import java.util.List;

/**
 * Represents a set of views in an iOS application.
 *
 * @author Dale Emery
 */
public interface IosView {
    /**
     * Send a message to each view represented by this IosView.
     *
     * @param name      the name of the message to send (an Objective-C selector).
     * @param arguments arguments to send with the message.
     * @return the value returned from each represented view.
     */
    List<String> sendMessage(String name, String... arguments);
}

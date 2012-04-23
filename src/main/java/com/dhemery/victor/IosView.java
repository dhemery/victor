package com.dhemery.victor;

import org.hamcrest.SelfDescribing;

import java.util.List;

/**
 * Represents one or more views in an iOS application.
 * @author Dale Emery
 */
public interface IosView extends SelfDescribing {
    /**
     * Send a message to each view represented by this IosView.
     * @param name the name of the message to send (an Objective-C selector).
     * @param arguments arguments to send with the message.
     * @return the value returned from each represented view.
     */
	List<String> sendMessage(String name, String... arguments);
}

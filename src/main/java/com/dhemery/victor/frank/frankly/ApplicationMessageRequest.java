package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.http.HttpRequest;

/**
 * <p>A request to send a message to an application's application delegate.</p>
 *
 * @author Dale Emery
 */
public class ApplicationMessageRequest extends HttpRequest {
    /**
     * @param message the message to send to the application.
     */
    public ApplicationMessageRequest(Message message) {
        super("app_exec", new MessageRequestBody(message));
    }
}

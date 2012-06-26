package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.By;
import com.dhemery.victor.http.HttpRequest;

/**
 * A request to send a message to the views identified by a query.
 *
 * @author Dale Emery
 */
public class ViewMessageRequest extends HttpRequest {
    /**
     * @param query   identifies the views that will receive the message.
     * @param message the message to send to each view.
     */
    public ViewMessageRequest(By query, Message message) {
        super("map", new ViewMessageRequestBody(query, message));
    }
}

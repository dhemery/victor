package com.dhemery.victor.frank;

import com.dhemery.victor.By;
import com.dhemery.victor.message.Message;
import com.dhemery.victor.http.HttpRequest;

/**
 * A request to send a message to each of a set of views that match a query.
 * @author Dale Emery
 *
 */
public class ViewMessageRequest extends HttpRequest {
	/**
	 * Constructs a map request to send a message to a set of views.
	 * @param query identifies the views that will receive the message.
	 * @param message the message to send to each view.
	 */
	public ViewMessageRequest(By query, Message message) {
		super("map", new ViewMessageRequestBody(query, message));
	}
}

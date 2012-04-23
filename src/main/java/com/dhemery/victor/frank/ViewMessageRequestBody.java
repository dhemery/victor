package com.dhemery.victor.frank;

import com.dhemery.victor.By;
import com.dhemery.victor.message.Message;

/**
 * A request to send a message to the views that match a query.
 * @author Dale Emery
 *
 */
public class ViewMessageRequestBody extends MessageRequestBody {
	public final String selector_engine;
	public final String query;

	/**
	 * Constructs a command to send a message to a set of views.
	 * @param query identifies the views that will receive the message.
	 * @param message the message to send to the views.
	 */
	public ViewMessageRequestBody(By query, Message message) {
		super(message);
		selector_engine = query.selectorEngine;
		this.query = query.selector;
	}

	@Override
	public String toString() {
		return String.format("%s:%s %s", selector_engine, query, super.toString());
	}
}

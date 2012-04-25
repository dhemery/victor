package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.By;
import com.dhemery.victor.frank.messages.Message;

/**
 * A request to send a message to the views identified a query.
 * @author Dale Emery
 */
public class ViewMessageRequestBody extends MessageRequestBody {
	public final String selector_engine;
	public final String query;

	/**
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
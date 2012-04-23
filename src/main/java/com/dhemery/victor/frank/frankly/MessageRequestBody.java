package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.frank.messages.Message;
import com.dhemery.victor.http.HttpPostBody;

/**
 * Carries a message to an object in an iOS application.
 * @author Dale Emery
 *
 */
public class MessageRequestBody extends HttpPostBody {
	public final Message operation;
	
	public MessageRequestBody(Message message) {
		operation = message;
	}
	
	@Override
	public String toString() {
		return operation.toString();
	}
}

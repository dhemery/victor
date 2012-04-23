package com.dhemery.victor.frank;

import com.dhemery.victor.message.Message;
import com.dhemery.victor.http.HttpPostBody;

/**
 * Carries a message to an entity in the application.
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

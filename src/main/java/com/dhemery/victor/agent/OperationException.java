package com.dhemery.victor.agent;


import com.dhemery.victor.view.Operation;
import com.dhemery.victor.view.OperationResponse;

public class OperationException extends RuntimeException {

	private OperationException(Operation operation, OperationResponse response) {
		this("Operation failed", operation, response);
	}

	public OperationException(String prefix, Operation operation, OperationResponse response) {
		super(messageAbout(prefix, operation, response));
	}

	private static String messageAbout(String prefix, Operation operation, OperationResponse response) {
        StringBuilder message = new StringBuilder();
		message.append(prefix).append("\n")
			.append("Operation was:\n")
			.append(operation.toString()).append("\n")
			.append("Results were:\n")
			.append(response.toString()).append("\n");
		return message.toString();
	}
}

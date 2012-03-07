package com.dhemery.victor.frank.drivers;

import com.dhemery.victor.frank.Operation;
import com.dhemery.victor.frank.ResultsResponse;

@SuppressWarnings("serial")
public class FrankViewOperationException extends RuntimeException {

	public FrankViewOperationException(Operation operation, ResultsResponse response) {
		this("Operation failed", operation, response);
	}

	public FrankViewOperationException(String prefix, Operation operation, ResultsResponse response) {
		super(messageAbout(prefix, operation, response));
	}

	private static String messageAbout(String prefix, Operation operation, ResultsResponse response) {
		StringBuffer message = new StringBuffer();
		message.append(prefix).append("\n")
			.append("Operation was:\n")
			.append(operation.toString()).append("\n")
			.append("Response was:\n")
			.append(response.toString()).append("\n");
		return message.toString();
	}
}

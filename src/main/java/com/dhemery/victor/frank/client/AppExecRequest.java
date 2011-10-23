package com.dhemery.victor.frank.client;

import com.dhemery.victor.frank.uiquery.Operation;
import com.dhemery.victor.frank.uiquery.OperationCommand;
import com.dhemery.victor.http.Request;

public class AppExecRequest extends Request {
	public AppExecRequest(OperationCommand command) {
		super("app_exec", command);
	}

	public AppExecRequest(Operation operation) {
		this(new OperationCommand(operation));
	}
}

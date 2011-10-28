package com.dhemery.victor.frank;


public class AppExecRequest extends Request {
	public AppExecRequest(OperationCommand command) {
		super("app_exec", command);
	}

	public AppExecRequest(Operation operation) {
		this(new OperationCommand(operation));
	}
}

package com.dhemery.victor.frank;

import com.dhemery.victor.application.server.Operation;

/**
 * <p>A Frank request to invoke an operation on an application's "application delegate."</p>
 * @author Dale Emery
 */
public class AppExecRequest extends Request {
	public AppExecRequest(OperationCommandRequestBody command) {
		super("app_exec", command);
	}

	/**
	 * @param operation the operation for the application delegate to perform.
	 */
	public AppExecRequest(Operation operation) {
		this(new OperationCommandRequestBody(operation));
	}
}

package com.dhemery.victor.frank;

/**
 * <p>A Frank request to invoke an operation on an application's "application delegate."</p>
 * @author Dale Emery
 */
public class AppExecRequest extends Request {
	public AppExecRequest(OperationCommand command) {
		super("app_exec", command);
	}

	/**
	 * @param operation the operation for the application delegate to perform.
	 */
	public AppExecRequest(Operation operation) {
		this(new OperationCommand(operation));
	}
}

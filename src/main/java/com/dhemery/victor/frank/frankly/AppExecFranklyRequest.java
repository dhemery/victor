package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.frank.Operation;

/**
 * <p>A Frank request to invoke an operation on an application's "application delegate."</p>
 * @author Dale Emery
 */
public class AppExecFranklyRequest extends FranklyRequest {
	public AppExecFranklyRequest(OperationFranklyRequestBody command) {
		super("app_exec", command);
	}

	/**
	 * @param operation the operation for the application delegate to perform.
	 */
	public AppExecFranklyRequest(Operation operation) {
		this(new OperationFranklyRequestBody(operation));
	}
}

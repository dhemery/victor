package com.dhemery.victor.frank;

import com.dhemery.victor.application.server.Operation;

/**
 * A command for some entity in an application to perform an operation.
 * @author Dale Emery
 *
 */
public class OperationCommandRequestBody extends PostRequestBody {
	public final Operation operation;
	
	public OperationCommandRequestBody(Operation operation) {
		this.operation = operation;
	}
	
	/**
	 * @return the operation to execute.
	 */
	public Operation operation() { return operation; }
	
	@Override
	public String toString() {
		return String.format("[operation:%s]", operation());
	}
}

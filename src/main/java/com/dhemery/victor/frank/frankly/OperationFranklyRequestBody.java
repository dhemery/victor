package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.frank.Operation;

/**
 * A command for some entity in an application to perform an operation.
 * @author Dale Emery
 *
 */
public class OperationFranklyRequestBody extends PostFranklyRequestBody {
	public final Operation operation;
	
	public OperationFranklyRequestBody(Operation operation) {
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

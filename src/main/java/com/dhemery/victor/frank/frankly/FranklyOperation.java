package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.frank.Operation;
import com.dhemery.victor.http.HttpPostBody;

/**
 * A command for some entity in an application to perform an operation.
 * @author Dale Emery
 *
 */
public class FranklyOperation extends HttpPostBody {
	public final Operation operation;
	
	public FranklyOperation(Operation operation) {
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

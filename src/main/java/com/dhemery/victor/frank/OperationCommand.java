package com.dhemery.victor.frank;

/**
 * An command for a view or an application delegate to execute an operation.
 * @author Dale Emery
 *
 */
public class OperationCommand extends PostRequestBody {
	public final Operation operation;
	
	public OperationCommand(Operation operation) {
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

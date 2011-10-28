package com.dhemery.victor.frank;


public class OperationCommand extends PostRequestBody {
	public final Operation operation;
	
	public OperationCommand(Operation operation) {
		this.operation = operation;
	}
	
	public Operation operation() { return operation; }
	
	@Override
	public String toString() {
		return String.format("[operation:%s]", operation());
	}
}

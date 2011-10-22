package com.dhemery.victor.frank.uiquery;

import com.dhemery.victor.http.PostRequestBody;

public class OperationCommand extends PostRequestBody {
	public final Operation operation;
	
	public OperationCommand(Operation operation) {
		this.operation = operation;
	}
}

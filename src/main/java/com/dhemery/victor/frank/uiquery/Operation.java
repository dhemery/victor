package com.dhemery.victor.frank.uiquery;

import java.util.ArrayList;
import java.util.Collection;

public class Operation {
	private final String method_name;
	private final Collection<String> arguments = new ArrayList<String>();
	
	public Operation(String methodName) {
		this.method_name = methodName;
	}
	
	public String methodName() { return method_name; }
	public Collection<String> arguments() { return arguments; }
}

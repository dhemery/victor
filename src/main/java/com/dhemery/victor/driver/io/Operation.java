package com.dhemery.victor.driver.io;

import java.util.ArrayList;
import java.util.Collection;

public class Operation {
	private String method_name;
	private Collection<String> arguments = new ArrayList<String>();
	
	public Operation(String methodName) {
		this.method_name = methodName;
	}
	
	public String methodName() { return method_name; }
	public Collection<String> arguments() { return arguments; }
}
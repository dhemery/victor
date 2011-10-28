package com.dhemery.victor.frank;

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
	
	@Override
	public String toString() {
		return String.format("[method_name:%s][arguments:%s]", methodName(), arguments());
	}
}

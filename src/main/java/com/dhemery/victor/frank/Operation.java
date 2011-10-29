package com.dhemery.victor.frank;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>An operation to be carried out by a view or by the application delegate.</p>
 * <p><strong>NOTE:</strong> Currently this class supports only methods that take no parameters.</p>
 * @author Dale Emery
 */
public class Operation {
	private final String method_name;
	private final Collection<String> arguments = new ArrayList<String>();
	
	public Operation(String methodName) {
		this.method_name = methodName;
	}

	/**
	 * @return the name of the method to be executed.
	 */
	public String methodName() { return method_name; }
	
	/**
	 * @return the list of arguments for the method.
	 */
	public Collection<String> arguments() { return arguments; }
	
	@Override
	public String toString() {
		return String.format("[method_name:%s][arguments:%s]", methodName(), arguments());
	}
}

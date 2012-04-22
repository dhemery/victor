package com.dhemery.victor.frank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * <p>
 * An operation to be performed by one or more target objects in an iOS application.
 * An operation is specified by a name and a list of arguments.
 * The operation name may refer to either a method or a property.
 * </p>
 * <p>
 * If the operation name refers to a method,
 * the operation invokes the method on each target,
 * passing it the the list of operation arguments.
 * The operation returns the values returned by the targets.
 * </P
 * <p>
 * If the operation name refers to a property,
 * the operation returns the target's value for the property.
 * </p>
 * @author Dale Emery
 */
public class Operation {
	public final String method_name;
	public final Collection<String> arguments = new ArrayList<String>();
	
	/**
	 * @param name the name of the method to call or property to retrieve.
	 * @param arguments arguments to the method.
	 */
	public Operation(String name, String...arguments) {
		this.method_name = name;
		this.arguments.addAll(Arrays.asList(arguments));
	}

	@Override
	public String toString() {
		return String.format("%s%s", method_name, arguments);
	}
}

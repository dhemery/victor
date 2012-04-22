package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.By;
import com.dhemery.victor.frank.Operation;

/**
 * A command to invoke an operation on the set of views that match a query.
 * @author Dale Emery
 *
 */
public class ViewOperation extends FranklyOperation {
	public final String selector_engine;
	public final String query;

	/**
	 * Constructs a command to invoke an operation on a set of views.
	 * @param query identifies the views that will perform the operation.
	 * @param operation the operation for the views to perform.
	 */
	public ViewOperation(By query, Operation operation) {
		super(operation);
		selector_engine = query.selectorEngine;
		this.query = query.selector;
	}

	@Override
	public String toString() {
		return String.format("%s:%s %s", selector_engine, query, super.toString());
	}
}

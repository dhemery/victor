package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.Query;
import com.dhemery.victor.frank.Operation;

/**
 * A request to invoke an operation on each of a set of views that match a query.
 * @author Dale Emery
 *
 */
public class PerformViewOperation extends FranklyRequest {
	/**
	 * Constructs a map request to perform an operation on a set of views.
	 * @param query identifies the views that will execute the operation.
	 * @param operation the operation for each matching view to execute.
	 */
	public PerformViewOperation(Query query, Operation operation) {
		super("map", new ViewOperation(query, operation));
	}
}

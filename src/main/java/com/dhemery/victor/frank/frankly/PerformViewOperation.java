package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.ViewSelector;
import com.dhemery.victor.frank.Operation;
import com.dhemery.victor.http.HttpRequest;

/**
 * A request to invoke an operation on each of a set of views that match a query.
 * @author Dale Emery
 *
 */
public class PerformViewOperation extends HttpRequest {
	/**
	 * Constructs a map request to perform an operation on a set of views.
	 * @param query identifies the views that will execute the operation.
	 * @param operation the operation for each matching view to execute.
	 */
	public PerformViewOperation(ViewSelector query, Operation operation) {
		super("map", new ViewOperation(query, operation));
	}
}

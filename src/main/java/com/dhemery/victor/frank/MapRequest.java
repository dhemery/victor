package com.dhemery.victor.frank;

import com.dhemery.victor.application.server.Operation;

/**
 * A request to invoke an operation on each of a set of views that match a query.
 * @author Dale Emery
 *
 */
public class MapRequest extends Request {
	public MapRequest(MapOperationCommandRequestBody body) {
		super("map", body);
	}

	/**
	 * Constructs a map request to perform an operation on a set of views.
	 * @param query a query to identify the views that will execute the operation.
	 * @param selectorEngine the selector engine that will interpret the query within the Frank server.
	 * @param operation the operation for each matching view to execute.
	 */
	public MapRequest(String selectorEngine, String query, Operation operation) {
		this(new MapOperationCommandRequestBody(selectorEngine, query, operation));
	}
}

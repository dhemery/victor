package com.dhemery.victor.frank;

import com.dhemery.victor.application.server.Operation;

/**
 * A command to invoke an operation on the set of views that match a query.
 * @author Dale Emery
 *
 */
public class MapOperationCommandRequestBody extends OperationCommandRequestBody {
	public final String selector_engine = "uiquery";
	public final String query;

	/**
	 * Constructs a command to invoke an operation on a set of views.
	 * @param query a query that selects the views that will perform the operation.
	 * @param operation the operation for the views to perform.
	 */
	public MapOperationCommandRequestBody(String query, Operation operation) {
		super(operation);
		this.query = query;
	}

	/**
	 * @return the query that selects views to perform the operation.
	 */
	public String query() { return query; }
	public String selectorEngine() { return selector_engine; }
	
	@Override
	public String toString() {
		return String.format("[query:%s][selector_engine:%s]%s", query(), selectorEngine(), super.toString());
	}
}

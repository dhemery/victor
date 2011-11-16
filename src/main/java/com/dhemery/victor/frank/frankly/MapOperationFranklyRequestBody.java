package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.Query;
import com.dhemery.victor.frank.Operation;

/**
 * A command to invoke an operation on the set of views that match a query.
 * @author Dale Emery
 *
 */
public class MapOperationFranklyRequestBody extends OperationFranklyRequestBody {
	public final String selector_engine;
	public final String query;

	/**
	 * Constructs a command to invoke an operation on a set of views.
	 * @param query identifies the views that will perform the operation.
	 * @param operation the operation for the views to perform.
	 */
	public MapOperationFranklyRequestBody(Query query, Operation operation) {
		super(operation);
		selector_engine = query.selectorEngine();
		this.query = query.selector();
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

package com.dhemery.victor.frank;

/**
 * A request to invoke an operation on each of a set of views that match a query.
 * @author Dale Emery
 *
 */
public class MapRequest extends Request {
	public MapRequest(MapCommand body) {
		super("map", body);
	}

	/**
	 * Constructs a map request to perform an operation on a set of views.
	 * @param query a query to identify the views that will execute the operation.
	 * @param operation the operation for each matching view to execute.
	 */
	public MapRequest(String query, Operation operation) {
		this(new MapCommand(query, operation));
	}

	/**
	 * Constructs a map request to retrieve a property from a set of views.
	 * @param query a query to identify the views whose property to retrieve.
	 * @param property the property to retrieve for each matching view.
	 */
	public MapRequest(String query, String property) {
		this(query, new Operation(property));
	}	
}

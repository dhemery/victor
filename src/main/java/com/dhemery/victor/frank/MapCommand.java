package com.dhemery.victor.frank;

/**
 * A command to invoke an operation on the set of views that match a query.
 * @author Dale Emery
 *
 */
public class MapCommand extends OperationCommand {
	public final String query;

	/**
	 * Constructs a command to invoke an operation on a set of views.
	 * @param query a query that selects the views that will perform the operation.
	 * @param operation the operation for the views to perform.
	 */
	public MapCommand(String query, Operation operation) {
		super(operation);
		this.query = query;
	}

	/**
	 * @return the query that selects views to perform the operation.
	 */
	public String query() { return query; }
	
	@Override
	public String toString() {
		return String.format("[query:%s]%s", query(), super.toString());
	}
}

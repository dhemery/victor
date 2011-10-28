package com.dhemery.victor.frank;

public class MapCommand extends OperationCommand {
	public final String query;

	public MapCommand(String query, Operation operation) {
		super(operation);
		this.query = query;
	}
	
	public String query() { return query; }
	
	@Override
	public String toString() {
		return String.format("[query:%s]%s", query(), super.toString());
	}
}

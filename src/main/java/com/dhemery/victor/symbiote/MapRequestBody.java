package com.dhemery.victor.symbiote;

public class MapRequestBody extends PostRequestBody {
	private final String query;
	private final Operation operation;

	public MapRequestBody(String query, Operation operation) {
		this.query = query;
		this.operation = operation;
	}
	
	public String query() { return query; }
	public Operation operation() { return operation; }
}

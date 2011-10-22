package com.dhemery.victor.frank.uiquery;

public class MapCommand extends OperationCommand {
	public final String query;

	public MapCommand(String query, Operation operation) {
		super(operation);
		this.query = query;
	}
}

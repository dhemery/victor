package com.dhemery.victor.frank;

import java.util.List;

public class ResultsResponse {
	private final boolean succeeded;
	private final List<String> results;
	private final String reason;
	private final String details;

	public ResultsResponse(boolean succeeded, List<String> results, String reason, String details) {
		this.succeeded = succeeded;
		this.results = results;
		this.reason = reason;
		this.details = details;
	}

	public boolean succeeded() { return succeeded; }
	public List<String> results() { return results; }
	public String details() { return details; }
	public String reason() { return reason; }
}

package com.dhemery.victor.frank;

import java.util.List;

/**
 * An Frank response that may include a list of property values or return values.
 * @author Dale Emery
 *
 */
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

	/**
	 * @return the detailed description of the failure, if the request failed. Otherwise null.
	 */
	public String details() { return details; }
	
	/**
	 * @return a general description of the failure, if the request failed. Otherwise null.
	 */
	public String reason() { return reason; }
	
	/**
	 * @return the list of results, if the request was successful. An empty list otherwise.
	 */
	public List<String> results() { return results; }

	/**
	 * @return true if the request was successful, false otherwise.
	 */
	public boolean succeeded() { return succeeded; }
	
	@Override
	public String toString() {
		if(succeeded()) {
			return String.format("OK: %s", results);
		} else {
			return String.format("FAILED: %s (%s)", reason, details);
		}
	}
}

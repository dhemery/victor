package com.dhemery.victor.frank;

import java.util.List;

/**
 * An application response that may include a list of results.
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
		StringBuilder builder = new StringBuilder();
		builder.append("[resultsResponse:");
		builder.append(String.format("[succeeded:%s]", succeeded()));
		if(succeeded()) {
			builder.append(String.format("[results:%s]", results));
		} else {
			builder.append(String.format("[reason:%s][details:%s]", reason, details));
		}
		builder.append("]");
		return builder.toString();
	}
}

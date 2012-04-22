package com.dhemery.victor.frank;

import java.util.List;

/**
 * An Frank response that may include a list of property values or return values.
 * @author Dale Emery
 *
 */
public class ResultsResponse {
	public final boolean succeeded;
	public final List<String> results;
	public final String reason;
	public final String details;

	public ResultsResponse(boolean succeeded, List<String> results, String reason, String details) {
		this.succeeded = succeeded;
		this.results = results;
		this.reason = reason;
		this.details = details;
	}

	@Override
	public String toString() {
		if(succeeded) return String.format("OK: %s", results);
        return String.format("FAILED: %s (%s)", reason, details);
	}
}

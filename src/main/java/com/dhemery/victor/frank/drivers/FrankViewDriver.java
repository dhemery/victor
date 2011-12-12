package com.dhemery.victor.frank.drivers;

import java.io.IOException;
import java.util.List;

import org.hamcrest.Description;

import com.dhemery.victor.Query;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.frank.FrankClient;
import com.dhemery.victor.frank.Operation;
import com.dhemery.victor.frank.ResultsResponse;

/**
 * A view driver that interacts with a view through a Frank server.
 * @author Dale Emery
 *
 */
public class FrankViewDriver implements ViewDriver {
	private final FrankClient frank;
	private final Query query;

	/**
	 * @param frank a Frank client that can interact with this view.
	 * @param query a query that identifies the views driven by this driver.
	 */
	public FrankViewDriver(FrankClient frank, Query query) {
		this.frank = frank;
		this.query = query;
	}

	private ResultsResponse call(String method, String...arguments) throws IOException {
		return perform(new Operation(method, arguments));
	}

	@Override
	public void flash() throws IOException {
		call("flash");
	}

	@Override
	public boolean isPresent() {
		String property = "accessibilityLabel";
		ResultsResponse response;
		try {
			response = call(property);
		} catch (IOException e) {
			return false;
		}
		if(!response.succeeded()) return false;
		List<String> values = response.results();
		return values.size() == 1;
	}

	@Override
	public boolean isVisible() {
		String property = "isHidden";
		ResultsResponse response;
		try {
			response = property(property);
		} catch (IOException e) {
			return false;
		}
		if(!response.succeeded()) return false;
		List<String> values = response.results();
		if(values.size() != 1) return false;
		boolean isHidden = Boolean.parseBoolean(values.get(0));
		return !isHidden;
	}

	private ResultsResponse perform(Operation operation) throws IOException {
		return frank.perform(query, operation);
	}

	private ResultsResponse property(String propertyName) throws IOException {
		return perform(new Operation(propertyName));
	}

	@Override
	public Query query() {
		return query;
	}

	@Override
	public void touch() throws IOException {
		call("touch");
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(toString());
	}

	@Override
	public String toString() {
		return query().toString();
	}

}

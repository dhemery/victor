package com.dhemery.victor.frank.drivers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;

import com.dhemery.victor.ViewSelector;
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
	private final ViewSelector selector;

	/**
	 * @param frank a Frank client that can interact with this view.
	 * @param selector a query that identifies the views driven by this driver.
	 */
	public FrankViewDriver(FrankClient frank, ViewSelector selector) {
		this.frank = frank;
		this.selector = selector;
	}

	private ResultsResponse call(String method, String...arguments) {
		return perform(new Operation(method, arguments));
	}

	@Override
	public void flash() {
		call("flash");
	}

	@Override
	public boolean isPresent() {
		ResultsResponse response = perform(new Operation("accessibilityLabel"));
		if(!response.succeeded()) return false;
		return isSingular(response.results());
	}

	@Override
	public boolean isVisible() {
		ResultsResponse response = perform(new Operation("isHidden"));
		if(!response.succeeded()) return false;
		List<String> results = response.results();
		return isSingular(results) && isFalse(results.get(0));
	}

	@Override
    public String property(String property) {
		Operation operation = new Operation(property);
		ResultsResponse response = perform(operation);
		if(!response.succeeded()) {
			throw new FrankViewOperationException(operation, response);
		}
		List<String> results = response.results();
		if(!isSingular(results)) {
			throw new FrankViewOperationException("Query must identify a single view", operation, response);
		}
		return response.results().get(0);
	}

	private boolean isFalse(String result) {
		return !Boolean.parseBoolean(result);
	}

	private boolean isSingular(List<String> results) {
		return results.size() == 1;
	}

	private ResultsResponse perform(Operation operation) {
		ResultsResponse response;
		try {
			response = frank.perform(selector, operation);
		} catch (IOException e) {
			response = new ResultsResponse(false, new ArrayList<String>(), null, null);
		}
		return response;
	}

	@Override
	public ViewSelector selector() {
		return selector;
	}

	@Override
	public void touch() {
		call("touch");
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(toString());
	}

	@Override
	public String toString() {
		return selector().toString();
	}

	@Override
	public void setText(String text) {
		call("setText:", text);
	}
}

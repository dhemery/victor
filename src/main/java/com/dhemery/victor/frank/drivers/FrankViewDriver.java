package com.dhemery.victor.frank.drivers;

import java.io.IOException;
import java.util.List;

import org.hamcrest.Description;

import com.dhemery.victor.ViewSelector;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.frank.FrankClient;
import com.dhemery.victor.frank.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A view driver that interacts with a view through a Frank server.
 * @author Dale Emery
 *
 */
public class FrankViewDriver implements ViewDriver {
    private final Logger log = LoggerFactory.getLogger(getClass());
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

	@Override
    public List<String> call(String method, String...arguments) {
        return perform(new Operation(method, arguments));
	}

	@Override
	public ViewDriver flash() {
		call("flash");
        return this;
	}

	@Override
	public boolean isPresent() {
        return isSingular(call("accessibilityLabel"));
	}

	@Override
	public boolean isVisible() {
		List<String> results = call("isHidden");
		return isSingular(results) && isFalse(results.get(0));
	}

	@Override
    public String property(String property) {
        return call(property).get(0);
	}

	private boolean isFalse(String result) {
		return !Boolean.parseBoolean(result);
	}

	private boolean isSingular(List<String> results) {
		return results.size() == 1;
	}

	private List<String> perform(Operation operation) {
        log.debug("Send: {} {}", selector, operation);
        try {
            List<String> results = frank.perform(selector, operation);
            log.debug("{} {} returned {}", new Object[] { selector, operation, results});
            return results;
        } catch (IOException cause) {
            throw new FrankIOException(operation, cause);
        }
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

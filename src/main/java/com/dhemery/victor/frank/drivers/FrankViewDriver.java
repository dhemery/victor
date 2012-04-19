package com.dhemery.victor.frank.drivers;

import java.io.IOException;
import java.util.List;

import com.dhemery.victor.Action;
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
    public void call(Action<? super ViewDriver> action) {
        action.executeOn(this);
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
	public void describeTo(Description description) {
		description.appendText(toString());
	}

	@Override
	public String toString() {
		return selector().toString();
	}
}

package com.dhemery.victor.view;

import com.dhemery.polling.Action;
import com.dhemery.victor.By;
import com.dhemery.victor.IosView;
import com.dhemery.victor.frank.Operation;
import com.dhemery.victor.frank.FrankIOException;
import org.hamcrest.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Interacts with a view through an agent.
 * @author Dale Emery
 *
 */
public class AgentBackedIosView implements IosView {
    private final Logger log = LoggerFactory.getLogger(getClass());
	private final IosViewAgent agent;
	private final By query;

	/**
	 * @param agent an agent that can interact with this view.
	 * @param query a query that identifies the views represented by this driver.
	 */
	public AgentBackedIosView(IosViewAgent agent, By query) {
		this.agent = agent;
		this.query = query;
	}

	@Override
    public List<String> call(String method, String...arguments) {
        return perform(new Operation(method, arguments));
	}

    @Override
    public void call(Action<? super IosView> action) {
        action.executeOn(this);
    }

    private List<String> perform(Operation operation) {
        log.debug("Send: {} {}", query, operation);
        try {
            List<String> results = agent.perform(query, operation);
            log.debug("{} {} returned {}", new Object[] {query, operation, results});
            return results;
        } catch (IOException cause) {
            throw new FrankIOException(operation, cause);
        }
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(toString());
	}

	@Override
	public String toString() {
		return query.toString();
	}
}

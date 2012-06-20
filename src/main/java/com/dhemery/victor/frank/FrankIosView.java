package com.dhemery.victor.frank;

import com.dhemery.victor.By;
import com.dhemery.victor.IosView;
import com.dhemery.victor.frank.frankly.ViewMessageRequest;
import com.dhemery.victor.frank.messages.Message;
import com.dhemery.victor.frank.messages.MessageException;
import com.dhemery.victor.frank.messages.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Interacts with a view through a {@code FrankAgent}.
 *
 * @author Dale Emery
 */
public class FrankIosView implements IosView {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final FrankAgent agent;
    private final By query;

    /**
     * @param agent an agent that can interact with this view.
     * @param query a query that identifies the views represented by this driver.
     */
    public FrankIosView(FrankAgent agent, By query) {
        this.agent = agent;
        this.query = query;
    }

    @Override
    public List<String> sendMessage(String name, Object... arguments) {
        Message message = new Message(name, arguments);
        log.debug("--> {} {}", query, message);
        MessageResponse response = agent.sendMessageRequest(new ViewMessageRequest(query, message));
        if (response.failed()) throw new MessageException(this, message, response);
        List<String> results = response.results();
        log.debug("<-- {} {} returned {}", new Object[]{query, message, results});
        return results;
    }

    @Override
    public By query() {
        return query;
    }

    @Override
    public String toString() {
        return query.toString();
    }
}

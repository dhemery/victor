package com.dhemery.victor.frank;

import com.dhemery.victor.By;
import com.dhemery.victor.IosView;
import com.dhemery.victor.frank.messages.Message;
import com.dhemery.victor.frank.messages.MessageException;
import com.dhemery.victor.frank.messages.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Interacts with a view through a {@code FrankViewAgent}.
 * @author Dale Emery
 *
 */
public class FrankIosView implements IosView {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final FrankViewAgent agent;
    private final By query;

    /**
     * @param agent an agent that can interact with this view.
     * @param query a query that identifies the views represented by this driver.
     */
    public FrankIosView(FrankViewAgent agent, By query) {
        this.agent = agent;
        this.query = query;
    }

    @Override
    public List<String> sendMessage(String name, String... arguments) {
        Message message = new Message(name, arguments);
        log.debug("Send: {} {}", query, message);
        MessageResponse response = agent.sendViewMessage(query, message);
        if(!response.succeeded) throw new MessageException(this, message, response);
        log.debug("{} {} returned {}", new Object[] {query, message, response.results});
        return response.results;
    }

    @Override
    public String toString() {
        return query.toString();
    }
}

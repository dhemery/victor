package com.dhemery.victor;

import java.util.List;

/**
 * A view driver that interacts with views through an {@link IosViewAgent}.
 *
 * @author Dale Emery
 */
public class AgentBackedIosView implements IosView {
    private final IosViewAgent agent;
    private final By query;

    /**
     * @param agent an agent that can interact with view.
     * @param query a query that identifies the views represented by this driver.
     */
    public AgentBackedIosView(IosViewAgent agent, By query) {
        this.agent = agent;
        this.query = query;
    }

    @Override
    public List<String> sendMessage(String name, Object... arguments) {
        return agent.sendMessage(query, name, arguments);
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

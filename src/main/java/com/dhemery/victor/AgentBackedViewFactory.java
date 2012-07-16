package com.dhemery.victor;

/**
 * A view factory that creates views backed by a view agent.
 */
public class AgentBackedViewFactory implements IosViewFactory {
    private final IosViewAgent agent;

    /**
     * Create a view factory that creates views backed by the specified view agent.
     * @param agent the view agent for use in the views created by this factory.
     */
    public AgentBackedViewFactory(IosViewAgent agent) {
        this.agent = agent;
    }

    /**
     * Create a view backed by this factory's view agent.
     * @param query a query that identifies a set of views.
     * @return a view backed by this factory's view agent.
     */
    @Override
    public IosView view(By query) {
        return new AgentBackedIosView(agent, query);
    }
}

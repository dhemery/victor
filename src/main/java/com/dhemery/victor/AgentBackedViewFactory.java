package com.dhemery.victor;

public class AgentBackedViewFactory implements IosViewFactory {

    private final IosViewAgent agent;

    public AgentBackedViewFactory(IosViewAgent agent) {
        this.agent = agent;
    }

    @Override
    public IosView view(By query) {
        return new AgentBackedIosView(agent, query);
    }
}

package com.dhemery.victor.frank;

import com.dhemery.victor.AgentBackedIosView;
import com.dhemery.victor.By;
import com.dhemery.victor.IosView;
import com.dhemery.victor.IosViewAgent;

import java.util.List;

public class FrankViewAgent implements IosViewAgent {
    private final FrankAgent frankly;

    public FrankViewAgent(FrankAgent frankly) {
        this.frankly = frankly;
    }

    @Override
    public List<String> sendMessage(By query, String name, Object... arguments) {
        return frankly.map(query.engine(), query.pattern(), name, arguments);
    }

    @Override
    public IosView view(By query) {
        return new AgentBackedIosView(this, query);
    }
}

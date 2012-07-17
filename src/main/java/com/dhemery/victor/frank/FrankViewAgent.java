package com.dhemery.victor.frank;

import com.dhemery.victor.*;

import java.util.List;

/**
 * A view agent that communicates with views through a Frank agent.
 */
public class FrankViewAgent implements IosViewAgent {
    private final Frank frank;

    /**
     * @param frank the Frank agent through which this view agent communicates with views.
     */
    public FrankViewAgent(Frank frank) {
        this.frank = frank;
    }

    @Override
    public List<String> sendMessage(By query, String name, Object... arguments) {
        return frank.map(query.language(), query.pattern(), name, arguments);
    }
}

package com.dhemery.victor.frank;

import com.dhemery.victor.*;

import java.util.List;

public class FrankViewAgent implements IosViewAgent {
    private final Frank frank;

    public FrankViewAgent(Frank frank) {
        this.frank = frank;
    }

    @Override
    public List<String> sendMessage(By query, String name, Object... arguments) {
        return frank.map(query.engine(), query.pattern(), name, arguments);
    }
}

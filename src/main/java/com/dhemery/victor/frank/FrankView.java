package com.dhemery.victor.frank;

import com.dhemery.victor.IosView;
import com.dhemery.victor.IosViewIdentifier;

import java.util.List;

public class FrankView implements IosView {
    private final Frank frank;
    private final IosViewIdentifier id;

    public FrankView(Frank frank, IosViewIdentifier id) {
        this.frank = frank;
        this.id = id;
    }

    @Override
    public IosViewIdentifier id() {
        return id;
    }

    @Override
    public List<String> sendMessage(String name, Object... arguments) {
        return frank.map(id().language(),  id().pattern(), name, arguments);
    }
}

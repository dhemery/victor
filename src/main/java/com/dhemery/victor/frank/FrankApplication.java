package com.dhemery.victor.frank;

import com.dhemery.victor.*;

/**
 * An application agent that communicates to an iOS application through a Frank agent.
 */
public class FrankApplication implements IosApplication {
    private final Frank frank;

    /**
     * @param frank the Frank agent through which this application agent communicates with the application.
     */
    public FrankApplication(Frank frank) {
        this.frank = frank;
    }

    @Override
    public boolean isRunning() {
        try {
            frank.accessibilityCheck();
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    @Override
    public Orientation orientation() {
        String orientationName = frank.orientation();
        try {
            return Orientation.valueOf(orientationName.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return Orientation.UNKNOWN;
        }
    }

    @Override
    public String sendMessage(String name, Object... arguments) {
        return frank.appExec(name, arguments);
    }

    @Override
    public void typeIntoKeyboard(String text) {
        frank.typeIntoKeyboard(text);
    }

    @Override
    public IosView view(IosViewIdentifier id) {
        return new FrankView(frank, id);
    }

    @Override
    public String toString() {
        return frank.toString();
    }
}

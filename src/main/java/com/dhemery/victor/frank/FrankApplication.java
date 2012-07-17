package com.dhemery.victor.frank;

import com.dhemery.victor.IosApplication;
import com.dhemery.victor.IosApplicationOrientation;

/**
 * An application agent that communicates to an iOS application through a Frank agent.
 */
public class FrankApplication implements IosApplication {
    private final Frank frank;

    /**
     * Create an application agent that communicates with an application through the specified Frank agent.
     */
    public FrankApplication(Frank frank) {
        this.frank = frank;
    }

    @Override
    public boolean isRunning() {
        return frank.ping();
    }

    @Override
    public IosApplicationOrientation orientation() {
        String orientationName = frank.orientation();
        try {
            return IosApplicationOrientation.valueOf(orientationName.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return IosApplicationOrientation.UNKNOWN;
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
}

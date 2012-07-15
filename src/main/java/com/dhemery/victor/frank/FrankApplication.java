package com.dhemery.victor.frank;

import com.dhemery.victor.IosApplication;
import com.dhemery.victor.IosApplicationOrientation;

public class FrankApplication implements IosApplication {
    private final Frank frank;

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

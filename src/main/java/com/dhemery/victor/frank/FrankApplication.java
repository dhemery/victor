package com.dhemery.victor.frank;

import com.dhemery.victor.IosApplication;
import com.dhemery.victor.IosApplicationOrientation;

public class FrankApplication implements IosApplication {
    private final FrankAgent frankly;

    public FrankApplication(FrankAgent frankly) {
        this.frankly = frankly;
    }

    @Override
    public boolean isRunning() {
        return frankly.ping();
    }

    @Override
    public IosApplicationOrientation orientation() {
        String orientationName = frankly.orientation();
        try {
            return IosApplicationOrientation.valueOf(orientationName.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return IosApplicationOrientation.UNKNOWN;
        }
    }

    @Override
    public String sendMessage(String name, Object... arguments) {
        return frankly.appExec(name, arguments);
    }

    @Override
    public void typeIntoKeyboard(String text) {
        frankly.typeIntoKeyboard(text);
    }
}

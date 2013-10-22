package com.dhemery.victor;

public class DefaultVictor implements Victor {
    private final IosApplication application;
    private final IosApplicationBundle bundle;
    private final IosDevice device;
    private final IosSdk sdk;

    public DefaultVictor(
            IosApplication application,
            IosApplicationBundle bundle,
            IosDevice device,
            IosSdk sdk) {
        this.application = application;
        this.bundle = bundle;
        this.device = device;
        this.sdk = sdk;
    }
    @Override
    public IosApplication application() {
        return application;
    }

    @Override
    public IosApplicationBundle applicationBundle() {
        return bundle;
    }

    @Override
    public IosDevice device() {
        return device;
    }

    @Override
    public IosSdk sdk() {
        return sdk;
    }
}

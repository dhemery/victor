package com.dhemery.victor;

public interface Action<S> {
    void executeOn(S view);
}

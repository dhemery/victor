package com.dhemery.victor.frank;

public interface FrankPublisher {
    void subscribe(FrankSubscriber subscriber);
    void unsubscribe(FrankSubscriber subscriber);
}

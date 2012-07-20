package com.dhemery.publishing;

/**
 * Distributes publications to subscribers.
 * The nature of the publications is left to implementors.
 * @param <S> the type of subscriber
 * @see Publisher
 */
public interface Distributor<S> {
    /**
     * Register the subscriber to receive publications from this distributor
     */
    void subscribe(S subscriber);

    /**
     * Remove the subscriber from this distributor's recipients
     */
    void unsubscribe(S subscriber);
}

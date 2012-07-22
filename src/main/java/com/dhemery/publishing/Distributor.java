package com.dhemery.publishing;

/**
 * Distributes publications to subscribers.
 * The nature of the publications is left to implementors.
 * @see Publisher
 */
public interface Distributor {
    /**
     * Register the subscribers to receive publications from this distributor
     */
    void subscribe(Object... subscriber);

    /**
     * Remove the subscribers from this distributor's recipients
     */
    void unsubscribe(Object... subscriber);
}

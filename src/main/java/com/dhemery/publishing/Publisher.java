package com.dhemery.publishing;

/**
 * Publishes publications.
 * The distribution of publications is left to implementors.
 * @param <P> the type of publication
 * @see Distributor
 */
public interface Publisher<P> {
    /**
     * Publish a publication
     */
    void publish(P publication);
}

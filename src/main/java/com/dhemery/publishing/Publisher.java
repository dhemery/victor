package com.dhemery.publishing;

/**
 * Publishes publications.
 * The distribution of publications is left to implementors.
 * @see Distributor
 */
public interface Publisher {
    /**
     * Publish a publication
     */
    void publish(Object publication);
}

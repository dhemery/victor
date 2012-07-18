package com.dhemery.victor.device;

/**
 * A service that can be started and stopped.
 */
public interface Service {
    /**
     * Start the service.
     */
    void start();

    /**
     * Stop the service.
     */
    void stop();
}

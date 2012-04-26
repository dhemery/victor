package com.dhemery.victor;

/**
 * Represents an iOS device (real or simulated).
 *
 * @author Dale Emery
 */
public interface IosDevice {
    void rotateLeft();

    void rotateRight();

    /**
     * Save an image of the screen to the desktop.
     */
    void saveScreenShot();

    /**
     * Start the device.
     */
    void start();

    /**
     * Stop the device.
     */
    void stop();
}

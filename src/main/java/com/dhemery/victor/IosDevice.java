package com.dhemery.victor;

/**
 * Represents an iOS device (real or simulated).
 *
 * @author Dale Emery
 */
public interface IosDevice {
    @VictorEntryPoint
    void rotateLeft();

    @VictorEntryPoint
    void rotateRight();

    /**
     * Save an image of the screen to the desktop.
     */
    @VictorEntryPoint
    void saveScreenShot();

    /**
     * Start the device.
     */
    @VictorEntryPoint
    void start();

    /**
     * Stop the device.
     */
    @VictorEntryPoint
    void stop();
}

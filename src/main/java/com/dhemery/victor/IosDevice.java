package com.dhemery.victor;

/**
 * A driver that interacts with a simulated iOS device.
 *
 * @author Dale Emery
 */
public interface IosDevice {
    /**
     * Rotate the simulated device left.
     */
    void rotateLeft();

    /**
     * Rotate the simulated device right.
     */
    void rotateRight();

    /**
     * Save an image of the simulated device's screen to the desktop.
     */
    void saveScreenShot();

    /**
     * Start the simulated device.
     */
    void start();

    /**
     * Stop the simulated device.
     */
    void stop();

    /**
     * Report the type of device simulated by this driver.
     * @return the type of device.
     */
    String type();
}

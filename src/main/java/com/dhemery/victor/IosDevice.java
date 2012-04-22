package com.dhemery.victor;

import org.hamcrest.SelfDescribing;

/**
 * Represents an iOS device (real or simulated).
 * @author Dale Emery
 *
 */
public interface IosDevice extends SelfDescribing {
	public void rotateLeft();
	public void rotateRight();

	/**
	 * Saves an image of the screen to the desktop.
	 */
	public void saveScreenShot();
}

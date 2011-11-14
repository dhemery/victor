package com.dhemery.victor;


import java.io.IOException;

/**
 * A driver that can interact with a running iPhone or other iOS device.
 * @author Dale Emery
 *
 */
public interface PhoneDriver {
	public boolean isEnabledForAccessibility() throws IOException;
	public void rotateLeft() throws IOException, InterruptedException;
	public void rotateRight() throws IOException, InterruptedException;
	
	/**
	 * Shuts down the phone.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void shutDown() throws IOException, InterruptedException;
}

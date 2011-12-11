package com.dhemery.victor;

import java.io.IOException;

import org.hamcrest.SelfDescribing;

/**
 * A driver that can interact with one or more views in an iOS application.
 * @author Dale Emery
 */
public interface ViewDriver extends SelfDescribing {
	
	/**
	 * Causes the view to flash visually.
	 * @throws IOException
	 */
	public void flash() throws IOException;
	
	/**
	 * @return {@code true} if the view is present, otherwise {@code false}.
	 */
	public boolean isPresent();
	
	/**
	 * @return {@code true} if the view is visible, otherwise {@code false}.
	 */
	public boolean isVisible();
	
	/**
	 * @return the query that identifies the views represented by this driver.
	 */
	public Query query();

	/**
	 * Touch the element.
	 * @throws IOException
	 */
	public void touch() throws IOException;
}

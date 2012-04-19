package com.dhemery.victor;

import org.hamcrest.SelfDescribing;

import java.util.List;

/**
 * A driver that can interact with one or more views in an iOS application.
 * @author Dale Emery
 */
public interface ViewDriver extends SelfDescribing {
    /**
     * Call a given method on each view represented by this driver.
     * @param method the method to call (an Objective-C selector).
     * @param arguments arguments to pass to the method.
     * @return the from each represented view.
     */
	public List<String> call(String method, String...arguments);

	/**
	 * Causes the view to flash visually.
	 */
	public ViewDriver flash();
	
	/**
	 * @return {@code true} if the view is present, otherwise {@code false}.
	 */
	public boolean isPresent();
	
	/**
	 * @return {@code true} if the view is visible, otherwise {@code false}.
	 */
	public boolean isVisible();
	
	/**
	 * @param propertyName the name of the property to retrieve.
	 * @return the value of the property for the view.
	 */
	public String property(String propertyName);

	/**
	 * @return the selector that identifies the views represented by this driver.
	 */
	public ViewSelector selector();

	/**
	 * Touch the element.
	 */
	public void touch();

	/**
	 * @param text the text to type into the view.
	 */
	void setText(String text);
}

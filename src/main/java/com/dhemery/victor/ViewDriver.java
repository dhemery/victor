package com.dhemery.victor;

import com.dhemery.polling.Action;
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
	List<String> call(String method, String... arguments);

    void call(Action<? super ViewDriver> action);

	/**
	 * @return the selector that identifies the views represented by this driver.
	 */
	ViewSelector selector();
}

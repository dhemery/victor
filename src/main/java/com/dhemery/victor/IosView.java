package com.dhemery.victor;

import com.dhemery.polling.Action;
import org.hamcrest.SelfDescribing;

import java.util.List;

/**
 * Represents one or more views in an iOS application.
 * @author Dale Emery
 */
public interface IosView extends SelfDescribing {
    /**
     * Call a given method on each view represented by this IosView.
     * @param method the method to call (an Objective-C selector).
     * @param arguments arguments to pass to the method.
     * @return the from each represented view.
     */
	List<String> call(String method, String... arguments);

    void call(Action<? super IosView> action);
}

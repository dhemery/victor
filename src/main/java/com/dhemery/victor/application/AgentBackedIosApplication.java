package com.dhemery.victor.application;

import com.dhemery.victor.IosApplication;
import org.hamcrest.Description;

/**
 * An application driver that interacts with an application through a Frank server.
 * @author Dale Emery
 */
public class AgentBackedIosApplication implements IosApplication {
	private final IosApplicationAgent agent;

	/**
	 * @param agent a Frank client that can interact with this application.
	 */
	public AgentBackedIosApplication(IosApplicationAgent agent) {
		this.agent = agent;
	}

    @Override
    public Orientation orientation() {
        OrientationResponse response = agent.orientation();
        String orientationName = response.orientation.toUpperCase();
        return Orientation.valueOf(orientationName);
    }

	@Override
	public void describeTo(Description description) {
		description.appendText(toString());
	}

	@Override
	public String toString() {
		return "the application";
	}
}

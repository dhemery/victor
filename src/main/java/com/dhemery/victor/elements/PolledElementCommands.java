package com.dhemery.victor.elements;

import com.dhemery.poller.Condition;
import com.dhemery.victor.driver.Poll;

public class PolledElementCommands implements ElementCommands {
	private final ElementCommands element;
	private final Condition ready;
	private final Poll poll = new Poll();

	public PolledElementCommands(ElementCommands element, Condition condition) {
		ready = condition;
		this.element = element;
	}

	public void whenReady() {
		poll.until(ready);
	}

	@Override
	public void touch() {
		whenReady();
		element.touch();
	}
}

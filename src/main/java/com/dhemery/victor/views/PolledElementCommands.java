package com.dhemery.victor.views;

import java.io.IOException;

import com.dhemery.poller.Condition;
import com.dhemery.poller.Poll;

public class PolledElementCommands implements ElementCommands {
	private final ElementCommands element;
	private final Condition ready;
	private final Poll poll;

	public PolledElementCommands(ElementCommands element, Condition condition, Poll poll) {
		this.element = element;
		this.ready = condition;
		this.poll = poll;
	}

	@Override
	public void flash() throws IOException {
		poll.until(ready);
		element.flash();
	}

	@Override
	public void touch() throws IOException {
		poll.until(ready);
		element.touch();
	}
}

package com.dhemery.victor.elements.polled;

import com.dhemery.poller.Condition;
import com.dhemery.poller.For;

public class Polled {
	private final long timeoutInMilliseconds = 20000;
	private final long pollingIntervalInMilliseconds = 1000;

	protected void pollUntil(Condition condition) {
		new For(timeoutInMilliseconds).poll().every(pollingIntervalInMilliseconds).until(condition);
	}
}

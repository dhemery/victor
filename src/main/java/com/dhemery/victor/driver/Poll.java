package com.dhemery.victor.driver;

import com.dhemery.poller.Condition;
import com.dhemery.poller.For;

public class Poll {
	private final long timeoutInMilliseconds = 20000;
	private final long pollingIntervalInMilliseconds = 1000;

	public void until(Condition condition) {
		new For(timeoutInMilliseconds).poll().every(pollingIntervalInMilliseconds).until(condition);
	}
}

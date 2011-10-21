package com.dhemery.victor.driver;

import com.dhemery.poller.Condition;
import com.dhemery.poller.For;

public class Poll {
	private final long timeoutInMilliseconds;
	private final long pollingIntervalInMilliseconds;

	public Poll(long timeoutInMilliseconds, long pollingIntervalInMilliseconds) {
		this.timeoutInMilliseconds = timeoutInMilliseconds;
		this.pollingIntervalInMilliseconds = pollingIntervalInMilliseconds;
	}

	public void until(Condition condition) {
		new For(timeoutInMilliseconds).poll().every(pollingIntervalInMilliseconds).until(condition);
	}
}

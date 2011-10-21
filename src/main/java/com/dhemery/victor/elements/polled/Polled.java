package com.dhemery.victor.elements.polled;

import com.dhemery.poller.Condition;
import com.dhemery.poller.For;

public class Polled {
	protected boolean pollUntil(Condition condition) {
		new For(20000).poll().until(condition);
		return false;
	}
}

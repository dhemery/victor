package com.dhemery.poller;

@SuppressWarnings("serial")
public class RequiredConditionException extends Exception {
	public RequiredConditionException(Condition condition) {
		super(String.format("Expected %s", condition.describe()));
	}
}

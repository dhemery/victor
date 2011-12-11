package com.dhemery.victor.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import com.dhemery.poller.Condition;

public class MatcherCondition<T> extends Condition {
	private final T subject;
	private final Matcher<T> matcher;

	public MatcherCondition(T subject, Matcher<T> matcher) {
		this.subject = subject;
		this.matcher = matcher;
	}

	@Override
	public String describe() {
		Description description = new StringDescription();
		matcher.describeTo(description);
		return description.toString();
	}

	@Override
	public boolean isSatisfied() {
		return matcher.matches(subject);
	}

	public static <T> MatcherConditionSubject<T> subject(T subject) {
		return new MatcherConditionSubject<T>(subject);
	}

	public static class MatcherConditionSubject<T> {
		private final T subject;

		public MatcherConditionSubject(T subject) {
			this.subject = subject;
		}


		public MatcherCondition<T> matches(Matcher<T> matcher) {
			return new MatcherCondition<T>(subject, matcher);
		}

		public MatcherCondition<T> is(Matcher<T> matcher) { return matches(matcher); }
		public MatcherCondition<T> has(Matcher<T> matcher) { return matches(matcher); }
	}
}

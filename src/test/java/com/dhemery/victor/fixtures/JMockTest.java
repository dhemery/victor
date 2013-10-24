package com.dhemery.victor.fixtures;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;

public class JMockTest {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();

    public void given(Expectations expectations) {
        expect(expectations);
    }

    public void expect(Expectations expectations) {
        context.checking(expectations);
    }
}

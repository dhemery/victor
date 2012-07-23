package com.dhemery.publishing;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;

public class ASubscriptionMethodFilter {
    private final Object subscriber = new Object(){
        @Subscribe
        public void validSubscription(Object o) {}
        public void methodNotAnnotatedAsSubscription(Object o) {}
        @Subscribe
        private void privateMethod(Object o) {}
        @Subscribe
        public Object nonVoidMethod(Object o) { return null; }
        @Subscribe
        public void zeroParameterMethod(){}
        @Subscribe
        public void multiParameterMethod(Object a, Object b){}
    };
    private final SubscriptionMethodFilter filter = new SubscriptionMethodFilter();


    @Test
    public void acceptsPublicVoidOneParameterMethodsAnnotatedAsSubscription() throws NoSuchMethodException {
        Method validSubscription = subscriber.getClass().getDeclaredMethod("validSubscription", Object.class);

        assertThat(filter, accepts(validSubscription));
    }

    @Test
    public void rejectsMethodsNotAnnotatedAsSubscription() throws NoSuchMethodException {
        Method methodNotAnnotatedAsSubscription = subscriber.getClass().getDeclaredMethod("methodNotAnnotatedAsSubscription", Object.class);

        assertThat(filter, rejects(methodNotAnnotatedAsSubscription));
    }

    @Test
    public void rejectsNonPublicMethods() throws NoSuchMethodException {
        Method privateMethod = subscriber.getClass().getDeclaredMethod("privateMethod", Object.class);
        assertThat(filter, rejects(privateMethod));
    }

    @Test
    public void rejectsNonVoidMethods() throws NoSuchMethodException {
        Method nonVoidMethod = subscriber.getClass().getDeclaredMethod("nonVoidMethod", Object.class);
        assertThat(filter, rejects(nonVoidMethod));
    }

    @Test
    public void rejectsMethodsWithNoParameters() throws NoSuchMethodException {
        Method zeroParameterMethod = subscriber.getClass().getDeclaredMethod("zeroParameterMethod");

        assertThat(filter, rejects(zeroParameterMethod));
    }

    @Test
    public void rejectsMethodsWithMultipleParameters() throws NoSuchMethodException {
        Method multiParameterMethod = subscriber.getClass().getDeclaredMethod("multiParameterMethod", Object.class, Object.class);

        assertThat(filter, rejects(multiParameterMethod));
    }

    private static Matcher<SubscriptionMethodFilter> accepts(final Method method) {
        return new TypeSafeDiagnosingMatcher<SubscriptionMethodFilter>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("accepts ").appendValue(method);
            }

            @Override
            protected boolean matchesSafely(SubscriptionMethodFilter filter, Description description) {
                description.appendText("rejected ").appendValue(method);
                return filter.accepts(method);
            }
        };
    }

    private static Matcher<SubscriptionMethodFilter> rejects(final Method method) {
        return new TypeSafeDiagnosingMatcher<SubscriptionMethodFilter>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("rejects ").appendValue(method);
            }

            @Override
            protected boolean matchesSafely(SubscriptionMethodFilter filter, Description description) {
                description.appendText("accepted ").appendValue(method);
                return !filter.accepts(method);
            }
        };
    }
}

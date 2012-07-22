package com.dhemery.publishing;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Accepts subscription methods and rejects non-subscription methods.
 */
public class SubscriptionMethodFilter {
    /**
     * Reports whether the method is a subscription method.
     * A subscription method is a method that:
     * <ul>
     * <li>is annotated as {@link Subscription}, and</li>
     * <li>is public, and</li>
     * <li>returns {@code void}, and</li>
     * <li>takes one parameter</li>
     * </ul>
     */
    public boolean accepts(Method method) {
        return isAnnotatedAsSubscription(method)
                && isPublic(method)
                && isVoid(method)
                && takesOneParameter(method)
                ;
    }

    private boolean isAnnotatedAsSubscription(Method method) {
        return method.isAnnotationPresent(Subscription.class);
    }

    private boolean isPublic(Method method) {
        return Modifier.isPublic(method.getModifiers());
    }

    private boolean isVoid(Method method) {
        return method.getReturnType().equals(Void.TYPE);
    }

    private boolean takesOneParameter(Method method) {
        return method.getParameterTypes().length == 1;
    }
}
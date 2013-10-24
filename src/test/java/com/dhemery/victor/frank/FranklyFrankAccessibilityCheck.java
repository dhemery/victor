package com.dhemery.victor.frank;

import com.dhemery.victor.frank.fixtures.FranklyFrankTest;
import com.dhemery.victor.frank.frankly.AccessibilityCheckResponse;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FranklyFrankAccessibilityCheck extends FranklyFrankTest {
    @Test
    public void returnsFalseIfAccessibilityCheckResponseIsFalse() {
        given(appRespondsTo(accessibilityCheck(), with(accessibility(false))));
        assertThat(frank.accessibilityCheck(), is(false));
    }

    @Test
    public void returnsTrueIfAccessibilityCheckResponseIsTrue() {
        given(appRespondsTo(accessibilityCheck(), with(accessibility(true))));
        assertThat(frank.accessibilityCheck(), is(true));
    }

    private static String accessibilityCheck() {
        return "/accessibility_check";
    }

    private AccessibilityCheckResponse accessibility(boolean result) {
        return new AccessibilityCheckResponse(result);
    }
}

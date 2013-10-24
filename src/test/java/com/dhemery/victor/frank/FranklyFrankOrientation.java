package com.dhemery.victor.frank;

import com.dhemery.victor.frank.fixtures.FranklyFrankTest;
import com.dhemery.victor.frank.frankly.OrientationResponse;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FranklyFrankOrientation extends FranklyFrankTest {
    @Test
    public void reportsLandscapeIfOrientationResponseIsLandscape() {
        given(appRespondsTo(orientationRequest(), with(orientationResponse("landscape"))));
        assertThat(frank.orientation(), is("landscape"));
    }

    @Test
    public void reportsPortraitIfOrientationResponseIsPortrait() {
        given(appRespondsTo(orientationRequest(), with(orientationResponse("portrait"))));
        assertThat(frank.orientation(), is("portrait"));
    }

    private String orientationRequest() {
        return "/orientation";
    }

    private OrientationResponse orientationResponse(String orientation) {
        return new OrientationResponse(orientation);
    }
}

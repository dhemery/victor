package com.dhemery.victor.frank;

import com.dhemery.victor.frank.fixtures.FranklyFrankTest;
import org.junit.Test;

import static com.dhemery.victor.frank.fixtures.FranklyMessageBuilder.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FranklyFrankAppExec extends FranklyFrankTest {

    @Test
    public void operationWithOnlyMethodName() {
        String methodName = "theMethodName";
        String appResponse = "Response to " + methodName;

        given(appRespondsTo(appExec(), operation(methodName), with(messageResponse(appResponse))));

        String response = frank.appExec(methodName);
        assertThat(response, is(appResponse));
    }

    @Test
    public void operationWithOneArgument() {
        String methodName = "theMethodName:";
        String argument = "argument";
        String appResponse = "Response to " + methodName;

        given(appRespondsTo(appExec(), operation(methodName, argument), with(messageResponse(appResponse))));

        String response = frank.appExec(methodName, argument);
        assertThat(response, is(appResponse));
    }

    @Test
    public void operationWithMultipleArguments() {
        String methodName = "the:Method:Name:";
        String argument1 = "argument1";
        String argument2 = "argument2";
        String argument3 = "argument3";
        String appResponse = "Response to " + methodName;

        given(appRespondsTo(appExec(), operation(methodName, argument1, argument2, argument3), with(messageResponse(appResponse))));

        String response = frank.appExec(methodName, argument1, argument2, argument3);
        assertThat(response, is(appResponse));
    }

    @Test(expected = IosOperationException.class)
    public void appExecThrowsIfRequestFails() {
        final String methodName = "theMethodName";

        given(appRespondsTo(appExec(), operation(methodName), with(failureMessageResponse())));

        frank.appExec(methodName);
    }

    private static String appExec() {
        return "/app_exec";
    }
}

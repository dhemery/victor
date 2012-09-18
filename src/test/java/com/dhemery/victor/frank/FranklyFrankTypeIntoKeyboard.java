package com.dhemery.victor.frank;

import com.dhemery.network.SerializingEndpoint;
import com.dhemery.victor.frank.frankly.TextToType;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class FranklyFrankTypeIntoKeyboard {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock public SerializingEndpoint endpoint;
    private Frank frank;

    @Before
    public void setup() {
        frank = new FranklyFrank(endpoint);
    }

    @Test
    public void sendsTypeIntoKeyboardRequestViaPut() {
        context.checking(new Expectations() {{
            oneOf(endpoint).put(with("/type_into_keyboard"), with(any(TextToType.class)), with(any(Class.class)));
        }});

        frank.typeIntoKeyboard("ignored");
    }

    @Test
    public void requestsVoidReturnType() {
        context.checking(new Expectations() {{
            oneOf(endpoint).put(with("/type_into_keyboard"), with(any(TextToType.class)), with(Void.class));
        }});

        frank.typeIntoKeyboard("ignored");
    }

    @Test
    public void requestIncludesTextToType() {
        final String textToType = "the text to type";
        context.checking(new Expectations(){{
            oneOf(endpoint).put(with("/type_into_keyboard"), with(new TextToType(textToType)), with(any(Class.class)));
        }});

        frank.typeIntoKeyboard(textToType);
    }
}

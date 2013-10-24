package com.dhemery.victor.frank;

import com.dhemery.victor.frank.fixtures.FranklyFrankTest;
import com.dhemery.victor.frank.frankly.TextToType;
import org.junit.Test;

public class FranklyFrankTypeIntoKeyboard extends FranklyFrankTest {
    @Test
    public void sendsTypeIntoKeyboardRequest() {
        expect(appReceives(typeIntoKeyboardRequest(), textToType("text to type")));
        frank.typeIntoKeyboard("text to type");
    }

    private String typeIntoKeyboardRequest() {
        return "/type_into_keyboard";
    }

    private TextToType textToType(String text) {
        return new TextToType(text);
    }
}

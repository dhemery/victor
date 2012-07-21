package com.dhemery.victor.frank.events;

public class WillRequestTypeIntoKeyboard {
    private final String text;

    public WillRequestTypeIntoKeyboard(String text) {
        this.text = text;
    }
    public String text() { return text; }
}

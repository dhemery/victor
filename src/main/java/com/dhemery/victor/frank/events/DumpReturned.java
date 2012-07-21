package com.dhemery.victor.frank.events;

public class DumpReturned {
    private final String viewTreeJson;

    public DumpReturned(String viewTreeJson) {
        this.viewTreeJson = viewTreeJson;
    }
    public String viewTreeJson() { return viewTreeJson; }
}

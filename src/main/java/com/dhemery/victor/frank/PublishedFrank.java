package com.dhemery.victor.frank;

import com.dhemery.network.Endpoint;
import com.google.common.eventbus.EventBus;

import java.util.List;

public class PublishedFrank implements Frank {
    private final EventBus publisher;
    private final Frank frank;

    public PublishedFrank(EventBus publisher, Frank frank) {
        this.publisher = publisher;
        this.frank = frank;
    }

    @Override
    public boolean accessibilityCheck() {
        publisher.post(new FrankEvent.WillRequestAccessibilityCheck());
        boolean enabled = frank.accessibilityCheck();
        publisher.post(new FrankEvent.AccessibilityCheckReturned(enabled));
        return enabled;
    }

    @Override
    public String appExec(String name, Object...arguments) {
        publisher.post(new FrankEvent.WillRequestAppExec(name, arguments));
        String result = frank.appExec(name, arguments);
        publisher.post(new FrankEvent.AppExecReturned(name, arguments, result));
        return result;
    }

    @Override
    public String dump() {
        publisher.post(new FrankEvent.WillRequestDump());
        String response = frank.dump();
        publisher.post(new FrankEvent.DumpReturned(response));
        return response;
    }

    @Override
    public List<String> map(String engine, String query, String name, Object...arguments) {
        publisher.post(new FrankEvent.WillRequestMap(engine, query, name, arguments));
        List<String> results = frank.map(engine, query, name, arguments);
        publisher.post(new FrankEvent.MapReturned(engine, query, name, arguments, results));
        return results;
    }

    @Override
    public String orientation() {
        publisher.post(new FrankEvent.WillRequestOrientation());
        String orientation = frank.orientation();
        publisher.post(new FrankEvent.OrientationReturned(orientation));
        return orientation;
    }

    @Override
    public void typeIntoKeyboard(String text) {
        publisher.post(new FrankEvent.WillRequestTypeIntoKeyboard(text));
        frank.typeIntoKeyboard(text);
        publisher.post(new FrankEvent.TypeIntoKeyboardReturned());
    }

    @Override
    public Endpoint endpoint() {
        return frank.endpoint();
    }
}

package com.dhemery.victor.frank.publishing;

import com.dhemery.network.Endpoint;
import com.dhemery.publishing.Publisher;
import com.dhemery.victor.frank.Frank;
import com.dhemery.victor.frank.events.*;

import java.util.List;

/**
 * <p>
 * Wraps a Frank agent and publishes events about each {@link com.dhemery.victor.frank.Frank} method call and each result.
 * For each method X, the wrapper publishes two events:
 * {@code WillRequestX} and {@code XReturned}.
 * </p>
 * <p>
 * The {@code WillRequestX} events include the method's arguments (if any).
 * </p>
 * <p>
 * Most of the {@code XReturned} events include method's arguments and return value.
 * </p>
 * <p>
 * For a complete roster of Frank events, see the {@link com.dhemery.victor.frank.FrankEvent} class.
 * </p>
 */
public class PublishingFrank implements Frank {
    private final Publisher<Object> publisher;
    private final Frank frank;

    public PublishingFrank(Publisher<Object> publisher, Frank frank) {
        this.publisher = publisher;
        this.frank = frank;
    }

    @Override
    public boolean accessibilityCheck() {
        publisher.publish(new WillRequestAccessibilityCheck());
        boolean enabled = frank.accessibilityCheck();
        publisher.publish(new AccessibilityCheckReturned(enabled));
        return enabled;
    }

    @Override
    public String appExec(String name, Object... arguments) {
        publisher.publish(new WillRequestAppExec(name, arguments));
        String result = frank.appExec(name, arguments);
        publisher.publish(new AppExecReturned(name, arguments, result));
        return result;
    }

    @Override
    public String dump() {
        publisher.publish(new WillRequestDump());
        String response = frank.dump();
        publisher.publish(new DumpReturned(response));
        return response;
    }

    @Override
    public List<String> map(String engine, String query, String name, Object... arguments) {
        publisher.publish(new WillRequestMap(engine, query, name, arguments));
        List<String> results = frank.map(engine, query, name, arguments);
        publisher.publish(new MapReturned(engine, query, name, arguments, results));
        return results;
    }

    @Override
    public String orientation() {
        publisher.publish(new WillRequestOrientation());
        String orientation = frank.orientation();
        publisher.publish(new OrientationReturned(orientation));
        return orientation;
    }

    @Override
    public void typeIntoKeyboard(String text) {
        publisher.publish(new WillRequestTypeIntoKeyboard(text));
        frank.typeIntoKeyboard(text);
        publisher.publish(new TypeIntoKeyboardReturned());
    }

    @Override
    public Endpoint endpoint() {
        return frank.endpoint();
    }
}

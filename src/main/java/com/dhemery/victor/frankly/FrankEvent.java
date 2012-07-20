package com.dhemery.victor.frankly;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FrankEvent {
    public static class WillRequestAccessibilityCheck {}

    public static class AccessibilityCheckReturned {
        public final boolean enabled;

        public AccessibilityCheckReturned(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public static class WillRequestAppExec {
        public final String name;
        public final List<Object> arguments;

        public WillRequestAppExec(String name, Object[] arguments) {
            this.name = name;
            this.arguments = Collections.unmodifiableList(Arrays.asList(arguments));
        }
    }

    public static class AppExecReturned {
        public final String name;
        public final List<Object> arguments;
        public final String returnValue;

        public AppExecReturned(String name, Object[] arguments, String returnValue) {
            this.name = name;
            this.arguments = Collections.unmodifiableList(Arrays.asList(arguments));
            this.returnValue = returnValue;
        }
    }

    public static class WillRequestDump {}

    public static class DumpReturned {
        public final String viewTreeJson;

        public DumpReturned(String viewTreeJson) {
            this.viewTreeJson = viewTreeJson;
        }
    }

    public static class WillRequestMap {
        public final String engine;
        public final String query;
        public final String name;
        public final List<Object> arguments;

        public WillRequestMap(String engine, String query, String name, Object[] arguments) {
            this.engine = engine;
            this.query = query;
            this.name = name;
            this.arguments = Collections.unmodifiableList(Arrays.asList(arguments));
        }
    }

    public static class MapReturned {
        public final String engine;
        public final String query;
        public final String name;
        public final List<Object> arguments;
        public final List<String> returnValues;

        public MapReturned(String engine, String query, String name, Object[] arguments, List<String> returnValues) {
            this.engine = engine;
            this.query = query;
            this.name = name;
            this.arguments = Collections.unmodifiableList(Arrays.asList(arguments));
            this.returnValues = Collections.unmodifiableList(returnValues);;
        }
    }

    public static class WillRequestOrientation {}

    public static class OrientationReturned {
        public final String orientation;

        public OrientationReturned(String orientation) {
            this.orientation = orientation;
        }
    }

    public static class WillRequestTypeIntoKeyboard {
        public final String text;

        public WillRequestTypeIntoKeyboard(String text) {
            this.text = text;
        }
    }

    public static class TypeIntoKeyboardReturned {}
}

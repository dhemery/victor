package com.dhemery.victor.frankly;

import com.google.common.collect.ImmutableList;

import java.util.List;

public interface FrankEvent {
    class WillRequestAccessibilityCheck {}

    class AccessibilityCheckReturned {
        public final AccessibilityCheckResponse response;

        public AccessibilityCheckReturned(AccessibilityCheckResponse response) {
            this.response = response;
        }
    }

    public class WillRequestAppExec {
        public final String name;
        public final List<Object> arguments;

        public WillRequestAppExec(String name, Object[] arguments) {
            this.name = name;
            this.arguments = ImmutableList.copyOf(arguments);
        }
    }

    public class AppExecReturned {
        public final String name;
        public final List<Object> arguments;
        public final MessageResponse response;

        public AppExecReturned(String name, Object[] arguments, MessageResponse response) {
            this.name = name;
            this.arguments = ImmutableList.copyOf(arguments);
            this.response = response;
        }
    }

    public class WillRequestDump {}

    public class DumpReturned {
        public final String response;

        public DumpReturned(String response) {
            this.response = response;
        }
    }

    public class WillRequestMap {
        public final String engine;
        public final String query;
        public final String name;
        public final List<Object> arguments;

        public WillRequestMap(String engine, String query, String name, Object[] arguments) {
            this.engine = engine;
            this.query = query;
            this.name = name;
            this.arguments = ImmutableList.copyOf(arguments);
        }
    }

    public class MapReturned {
        public final String engine;
        public final String query;
        public final String name;
        public final List<Object> arguments;
        public final MessageResponse response;

        public MapReturned(String engine, String query, String name, Object[] arguments, MessageResponse response) {
            this.engine = engine;
            this.query = query;
            this.name = name;
            this.arguments = ImmutableList.copyOf(arguments);
            this.response = response;
        }
    }

    public class WillRequestOrientation {}

    public class OrientationReturned {
        public final OrientationResponse response;

        public OrientationReturned(OrientationResponse response) {
            this.response = response;
        }
    }

    public class WillRequestTypeIntoKeyboard {
        public final String text;

        public WillRequestTypeIntoKeyboard(String text) {
            this.text = text;
        }
    }

    public class TypeIntoKeyboardReturned {}
}

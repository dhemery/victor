package com.dhemery.os;

public class OSCommandEvent {
    public static class WillRun {
        public final OSCommand command;

        public WillRun(OSCommand command) {
            this.command = command;
        }
    }

    public static class Ran {
        public final OSCommand command;

        public Ran(OSCommand command) {
            this.command = command;
        }
    }

    public static class Returned {
        public final OSCommand command;
        public final String output;

        public Returned(OSCommand command, String output) {
            this.command = command;
            this.output = output;
        }
    }
}

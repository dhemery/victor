package com.dhemery.victor.os;

public interface CommandListener {
    void willRun(Command command);
    void started(Command command, Process process);
    void returned(Command command, String output);
}

package com.dhemery.victor.os;

public interface ShellListener {
    void willRun(Command command);
    void started(Command command, Process process);
    void returned(Command command, String output);
}

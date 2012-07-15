package com.dhemery.victor;

public interface OSCommandListener {
    void willRun(OSCommand command);
    void started(OSCommand command, Process process);
    void returned(OSCommand command, String output);
}

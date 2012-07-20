package com.dhemery.os;

import com.google.common.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PublishingOSProcess implements OSProcess {
    private final OSCommand command;
    private final Process process;
    private final EventBus eventBus;

    public PublishingOSProcess(OSCommand command, Process process, EventBus eventBus) {
        this.command = command;
        this.process = process;
        this.eventBus = eventBus;
    }

    @Override
    public String output() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        try {
            String output = reader.readLine();
            eventBus.post(new OSCommandEvent.Returned(command, output));
            return output;
        } catch (IOException cause) {
            throw new OSCommandException(command, cause);
        }
    }
}

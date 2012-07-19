package com.dhemery.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PublishingProcess implements OSProcess {
    private final OSCommandSubscriber publish;
    private final OSCommand command;
    private final Process process;

    public PublishingProcess(OSCommandSubscriber publisher, OSCommand command, Process process) {
        publish = publisher;
        this.command = command;
        this.process = process;
    }

    @Override
    public String output() {
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String output = bufferedReader.readLine();
            publish.returned(command, output);
            return output;
        } catch (IOException cause) {
            throw new OSCommandException(command, cause);
        }
    }
}

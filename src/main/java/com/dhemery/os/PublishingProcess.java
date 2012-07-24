package com.dhemery.os;

import com.dhemery.os.OSCommand;
import com.dhemery.os.OSProcess;
import com.dhemery.os.events.Returned;
import com.dhemery.publishing.Publisher;

/**
 * Wraps an OSProcess and publishes events about its output.
 * <p>
 * After each call to {@link #output()}
 * the wrapper publishes a {@link com.dhemery.os.events.Returned} event.
 * </p>
 */
public class PublishingProcess implements OSProcess {
    private final Publisher publisher;
    private final OSCommand command;
    private final OSProcess process;

    /**
     * Wrap the given process and publish events when its {@link #output()} method is called.
     * @param publisher the publisher through which to publish events
     * @param command the command that launched the process
     * @param process the process to wrap
     */
    public PublishingProcess(Publisher publisher, OSCommand command, OSProcess process) {
        this.publisher = publisher;
        this.command = command;
        this.process = process;
    }

    /**
     * Publish and return the output from the underlying process.
     */
    @Override
    public String output() {
        String output = process.output();
        publisher.publish(new Returned(command, output));
        return output;
    }
}

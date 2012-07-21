package com.dhemery.os;

/**
 * Represents an operating system process.
 * The represented process may or may not have terminated
 * by the time this representation is created.
 */
public interface OSProcess {
    /**
     * Retrieve the output from the represented operating system process.
     */
    String output();
}

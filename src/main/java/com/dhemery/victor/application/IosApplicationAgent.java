package com.dhemery.victor.application;

public interface IosApplicationAgent {
    /**
     * Determines the current orientation (portrait or landscape)
     * of the application in which the Frank server is running.
     * @return a response that describes the application's orientation.
     * @throws java.io.IOException
     */
    OrientationResponse orientation();
}

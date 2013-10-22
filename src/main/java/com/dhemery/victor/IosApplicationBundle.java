package com.dhemery.victor;

import com.dhemery.osx.ApplicationBundle;

import java.util.List;

/**
 * Represents an iOS application bundle.
 */
public interface IosApplicationBundle extends ApplicationBundle {
    /**
     * The list of device types on which this application can run.
     */
    List<String> deviceTypes();

    /**
     * The canonical name of the bundle's target iOS SDK.
     */
    String targetSdkCanonicalName();
}

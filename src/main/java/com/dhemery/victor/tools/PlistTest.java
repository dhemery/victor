package com.dhemery.victor.tools;

import com.dhemery.victor.configuration.IosApplicationBundle;

public class PlistTest {
    public static void
    main(String[] args) {
        IosApplicationBundle bundle = new IosApplicationBundle("/Users/Dale/Dropbox/Programming/workday/iphone-app");
        System.out.println(bundle.deviceTypes());
        System.out.println(bundle.bundleVersion());
    }
}

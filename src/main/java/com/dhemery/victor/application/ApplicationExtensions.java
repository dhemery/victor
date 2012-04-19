package com.dhemery.victor.application;

import com.dhemery.polling.Query;
import com.dhemery.victor.ApplicationDriver;

public class ApplicationExtensions {
    public static Query<ApplicationDriver, ApplicationDriver.Orientation> orientation() {
        return new OrientationQuery();
    }

}

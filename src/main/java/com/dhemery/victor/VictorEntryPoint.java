package com.dhemery.victor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@SuppressWarnings("ClassIndependentOfModule")
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface VictorEntryPoint {
}

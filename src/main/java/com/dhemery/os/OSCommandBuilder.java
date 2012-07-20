package com.dhemery.os;

import com.google.common.base.Supplier;

import java.util.List;
import java.util.Map;

public interface OSCommandBuilder<T extends OSCommand> extends Supplier<T> {
    OSCommandBuilder<T> withArgument(String argument);
    OSCommandBuilder<T> withArguments(String... arguments);
    OSCommandBuilder<T> withArguments(List<String> arguments);
    OSCommandBuilder<T> withEnvironment(Map<String, String> environment);
}
